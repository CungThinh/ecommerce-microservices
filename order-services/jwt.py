from typing import List

from fastapi import Security, HTTPException, Depends
from fastapi.security import HTTPBearer, HTTPAuthorizationCredentials
from jose import jwt, JWTError
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

def get_user_id(payload: dict = Depends(extract_token)) -> str:
    """Extracts the user's id from the JWT payload."""
    return payload.get("sub", "")

class RoleChecker:
    def __init__(self, allowed_roles: List[str]) -> None:
        self.allowed_roles = allowed_roles

    def __call__(self, roles: List[str] = Depends(get_user_scope)) -> None:
        print(f"Required roles: {self.allowed_roles}")
        print(f"User roles: {roles}")

        if any(role in self.allowed_roles for role in roles):
            return

        raise InSufficientPermission()