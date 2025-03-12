from http.client import responses
from typing import List

from fastapi import HTTPException
from fastapi.encoders import jsonable_encoder
from sqlmodel import select
from sqlmodel.ext.asyncio.session import AsyncSession

from checkout.schemas import CheckOutResponse
from config import Config
from order.schemas import CreateOrderRequest, ProductReserveResponse
from order.models import Order
from exceptions import ProductOutOfStock, UserUnauthorized
import httpx


class OrderService:
    @staticmethod
    async def create_order(request: CreateOrderRequest, session: AsyncSession, user_id: str):

        async with httpx.AsyncClient() as client:
            try:
                payload = jsonable_encoder({
                    "cartId": request.cart_id,
                    "cartProducts": [{
                        "productId": product.product_id,
                        "quantity": product.quantity
                    } for product in request.order_products]
                })
                product_services_url = Config.PRODUCT_SERVICES_URL
                response = await client.post(f"{product_services_url}/products/reserve", json=payload)
                data = ProductReserveResponse(**response.json().get("data", {}))
                for product_id, is_reserved in data.productReservationStatus.items():
                    if not is_reserved:
                        raise ProductOutOfStock()
            except httpx.HTTPStatusError as e:
                error_data = e.response.json()
                raise HTTPException(
                    status_code=e.response.status_code,
                    detail={
                        "code": error_data.get("code", 0),
                        "message": error_data.get("message", "Unknown error"),
                    }
                )
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

    @staticmethod
    async def get_orders_by_user_id(user_id: str, session: AsyncSession) -> List[Order]:
        result = await session.execute(select(Order).where(Order.user_id == user_id))
        return result.scalars().all()
