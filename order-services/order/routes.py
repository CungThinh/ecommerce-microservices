from fastapi import APIRouter, Depends
from sqlmodel.ext.asyncio.session import AsyncSession
from starlette import status

from database import get_session
from jwt import RoleChecker, get_user_id
from order.schemas import CreateOrderRequest
from order.service import OrderService
from global_schemas import ApiResponse

order_router = APIRouter()
user_role_checker = RoleChecker(["ROLE_USER"])
admin_role_checker = RoleChecker(["ROLE_ADMIN"])
order_service = OrderService()

@order_router.post("")
async def create_order(request: CreateOrderRequest,
                       db: AsyncSession = Depends(get_session),
                       user_id: str = Depends(get_user_id),
                        status_code=status.HTTP_201_CREATED):
    new_order = await order_service.create_order(request, user_id, db)
    return new_order

@order_router.get("/{user_id}")
async def get_orders_by_user_id(user_id: str, db: AsyncSession = Depends(get_session)):
    orders = await order_service.get_orders_by_user_id(user_id, db)
    return

@order_router.post("/cancel/{order_id}", response_model=ApiResponse, dependencies=[Depends(user_role_checker)])
async def cancel_order(order_id: int, user_id: str = Depends(get_user_id), db: AsyncSession = Depends(get_session)):
    await order_service.cancel_orders(order_id, user_id, db)
    return ApiResponse.success_response(
        data=None,
        message="Order đã được hủy"
    )
