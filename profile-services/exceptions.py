from typing import Callable, Any

from fastapi import FastAPI
from fastapi.requests import Request
from fastapi.responses import JSONResponse
from fastapi import status

class MyCustomException(Exception):
    pass

class ProfileAlreadyExists(MyCustomException):
    pass

class ProfileNotFound(MyCustomException):
    pass

def create_exception_handler(
        status_code: int, initial_detail: Any
) -> Callable[[Request, Exception], JSONResponse]:

    async def exception_handler(request: Request, exc: MyCustomException):

        return JSONResponse(content=initial_detail, status_code=status_code)
    return exception_handler

def register_all_errors(app: FastAPI):
    app.add_exception_handler(
        ProfileAlreadyExists,
        create_exception_handler(
            status_code=status.HTTP_400_BAD_REQUEST,
            initial_detail= {
                "message": "Profile của user đã tồn tại",
                "error_code": "profile_already_exists"
            }
        )
    )

    app.add_exception_handler(
        ProfileNotFound,
        create_exception_handler(
            status_code=status.HTTP_404_NOT_FOUND,
            initial_detail= {
                "message": "Profile không tồn tại",
                "error_code": "profile_not_found"
            }
        )
    )