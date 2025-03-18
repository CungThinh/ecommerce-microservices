from typing import Callable
from fastapi import FastAPI, Request, status
from fastapi.encoders import jsonable_encoder
from fastapi.responses import JSONResponse
from datetime import datetime
from global_schemas import ApiResponse  # Assuming your ApiResponse is in this module

class MyCustomException(Exception):
    pass

class ProductOutOfStock(MyCustomException):
    pass

class InSufficientPermission(MyCustomException):
    pass

class UserUnauthorized(MyCustomException):
    pass

class OrderNotFound(MyCustomException):
    pass

def create_exception_handler(
        status_code: int,
        error_code: int,
        message: str
) -> Callable[[Request, Exception], JSONResponse]:

    async def exception_handler(request: Request, exc: MyCustomException):
        error_response = ApiResponse.error_response(
            code=error_code,
            message=message
        )
        return JSONResponse(content=jsonable_encoder(error_response), status_code=status_code)

    return exception_handler

def register_all_errors(app: FastAPI):
    app.add_exception_handler(
        ProductOutOfStock,
        create_exception_handler(
            status_code=status.HTTP_400_BAD_REQUEST,
            error_code=7001,
            message="Một số sản phẩm đã được cập nhật, vui lòng quay lại giỏ hàng"
        )
    )

    app.add_exception_handler(
        InSufficientPermission,
        create_exception_handler(
            status_code=status.HTTP_403_FORBIDDEN,
            error_code=7002,
            message="Không đủ quyền truy cập"
        )
    )

    app.add_exception_handler(
        UserUnauthorized,
        create_exception_handler(
            status_code=status.HTTP_401_UNAUTHORIZED,
            error_code=7003,
            message="User chưa được xác thực"
        )
    )

    app.add_exception_handler(
        OrderNotFound,
        create_exception_handler(
            status_code=status.HTTP_404_NOT_FOUND,
            error_code=7004,
            message="Không tìm thấy order"
        )
    )