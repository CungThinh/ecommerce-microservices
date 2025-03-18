from sqlmodel import select
from sqlmodel.ext.asyncio.session import AsyncSession

from config import Config
from order.schemas import CreateOrderRequest
from order.models import Order
from exceptions import UserUnauthorized, OrderNotFound
from order.constants import OrderStatus
from clients.product_service_client import ProductServiceClient


class OrderService:
    def __init__(self):
        self.product_services_url = Config.PRODUCT_SERVICES_URL
        self.product_service_client = ProductServiceClient()

    async def create_order(self, request: CreateOrderRequest, user_id: str, session: AsyncSession) -> Order:
        await self.product_service_client.reserve_products(request.cart_id, request.order_products)
        if request.user_id != user_id:
            raise UserUnauthorized()

        order = Order(
            user_id = user_id,
            shipping_address = request.shipping_address.model_dump(),
            checkout_order = request.checkout_order.model_dump(),
            order_products = [item.model_dump() for item in request.order_products]
        )

        # session.add(order)
        # await session.flush()
        #
        # checkout_order = CheckOutOrder(**request.checkout_order.model_dump())
        # checkout_order.order_id = order.id
        # session.add(checkout_order)
        #
        # for product_data in request.order_products:
        #     order_product = OrderProducts(
        #         product_id=product_data.product_id,
        #         quantity=product_data.quantity,
        #         price=product_data.price,
        #         order_id=order.id
        #     )
        #     session.add(order_product)

        session.add(order)
        await session.flush()
        await session.commit()
        await session.refresh(order)
        return order

    async def get_order_by_id(self, order_id: int, session: AsyncSession) -> Order:
        statement = select(Order).where(Order.id == order_id)
        result = await session.execute(statement)
        return result.scalars().one_or_none()

    async def get_orders_by_user_id(self, user_id: str, session: AsyncSession):
        result = await session.execute(select(Order).where(Order.user_id == user_id))
        return result.scalars().all()

    async def cancel_orders(self, order_id: int, user_id: str, session: AsyncSession):
        order = await self.get_order_by_id(order_id, session)

        if order is None:
            raise OrderNotFound()

        if order.user_id != user_id:
            raise UserUnauthorized()

        order.status = OrderStatus.CANCELLED
        session.add(order)
        await session.commit()
        await session.refresh(order)
