from fastapi import APIRouter, Depends
from sqlmodel.ext.asyncio.session import AsyncSession

from database import get_session
from jwt import RoleChecker, get_user_id
from order.schemas import CreateOrderRequest
from order.service import OrderService

order_router = APIRouter()
user_role_checker = RoleChecker(["ROLE_USER"])


@order_router.post("")
async def create_order(request: CreateOrderRequest,
                       db: AsyncSession = Depends(get_session),
                       user_id: str = Depends(get_user_id)):
    new_order = await OrderService.create_order(request, db, user_id)
    return new_order

@order_router.get("/{user_id}")
async def get_orders_by_user_id(user_id: str, db: AsyncSession = Depends(get_session)):
    orders = await OrderService.get_orders_by_user_id(user_id, db)
    return orders
