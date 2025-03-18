from typing import TypeVar, Generic
from pydantic import BaseModel
from datetime import datetime

T = TypeVar('T')

class ApiResponse(BaseModel, Generic[T]):
    success: bool
    code: int
    data: T
    message: str
    timestamp: datetime

    @classmethod
    def success_response(cls, data: T, message: str):
        return cls(
            success=True,
            code=1000,
            data=data,
            message=message,
            timestamp=datetime.now()
        )

    @classmethod
    def error_response(cls, code: int, message: str):
        return cls(
            success=False,
            code=code,
            data=None,
            message=message,
            timestamp=datetime.now()
        )
