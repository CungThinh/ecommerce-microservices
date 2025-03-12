from typing import Callable, Any

from fastapi import FastAPI
from fastapi.requests import Request
from fastapi.responses import JSONResponse
from fastapi import status

class MyCustomException(Exception):
    pass

class ProductOutOfStock(MyCustomException):
    pass

class InSufficientPermission(MyCustomException):
    pass

class UserUnauthorized(MyCustomException):
    pass

def create_exception_handler(
        status_code: int, initial_detail: Any
) -> Callable[[Request, Exception], JSONResponse]:

    async def exception_handler(request: Request, exc: MyCustomException):

        return JSONResponse(content=initial_detail, status_code=status_code)
    return exception_handler

def register_all_errors(app: FastAPI):
    app.add_exception_handler(
        ProductOutOfStock,
        create_exception_handler(
            status_code=status.HTTP_400_BAD_REQUEST,
            initial_detail= {
                "message": "Một số sản phẩm đã được cập nhật, vui lòng quay lại giỏ hàng",
                "error_code": "product_out_of_stock"
            }
        )
    )

    app.add_exception_handler(
        InSufficientPermission,
        create_exception_handler(
            status_code=status.HTTP_403_FORBIDDEN,
            initial_detail= {
                "message": "Không đủ quyền truy cập",
                "error_code": "insufficient_permission"
            }
        )
    )

    app.add_exception_handler(
        UserUnauthorized,
        create_exception_handler(
            status_code=status.HTTP_401_UNAUTHORIZED,
            initial_detail= {
                "message": "User chưa được xác thực",
                "error_code": "user_unauthorized"
            }
        )
    )