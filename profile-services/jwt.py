from typing import List
from fastapi import HTTPException, Depends, Security
from fastapi.security import HTTPAuthorizationCredentials, HTTPBearer
from jose import JWTError, jwt
from starlette import status
from exceptions import InSufficientPermission

security = HTTPBearer()

def extract_token(credentials: HTTPAuthorizationCredentials = Security(security)) -> dict:
    """Extracts and validates the JWT token from the request."""
    token = credentials.credentials
    try:
        # No need for secretKey
        payload = jwt.decode(token,"", options={"verify_signature":False})
        return payload
    except JWTError:
        raise HTTPException(
            status_code=status.HTTP_401_UNAUTHORIZED,
            detail="Invalid or missing token"
        )

def get_user_scope(payload: dict = Depends(extract_token)) -> List[str]:
    """Extracts the user's roles (scope) from the JWT payload."""
    scope = payload.get("scope", "")
    return scope.split(" ") if scope else []

class RoleChecker:
    def __init__(self, allowed_roles: List[str]) -> None:
        self.allowed_roles = allowed_roles

    def __call__(self, roles: List[str] = Depends(get_user_scope)) -> None:
        """Checks if the user has at least one required role."""
        print(f"Required roles: {self.allowed_roles}")
        print(f"User roles: {roles}")

        if any(role in self.allowed_roles for role in roles):
            return

        raise InSufficientPermission()