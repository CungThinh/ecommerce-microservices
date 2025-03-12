from fastapi import APIRouter

from .service import CheckOutService
from .schemas import CheckOutRequest, CheckOutResponse

checkout_router = APIRouter()
checkout_service = CheckOutService()

@checkout_router.post("")
async def create_checkout(request: CheckOutRequest) -> CheckOutResponse:
    return await checkout_service.create_checkout(request)