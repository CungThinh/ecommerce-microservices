import httpx
from fastapi import HTTPException
from fastapi.encoders import jsonable_encoder

from checkout.schemas import CheckOutRequest, ValidateForCheckOutResponse, CheckOutResponse, CheckOutOrder
from clients.product_service_client import ProductServiceClient


class CheckOutService:
    def __init__(self):
        self.product_service_client = ProductServiceClient()

    async def create_checkout(self, checkout_request: CheckOutRequest):
        data = await self.product_service_client.validate_checkout(checkout_request)

        total_checkout_price = sum(
            product.price * product.quantity for product in data.cart_products
        )

        return CheckOutResponse(
            cart_products=data.cart_products,
            cart_id=data.cart_id,
            user_id=checkout_request.user_id,
            checkout_order=CheckOutOrder(
                total_price=total_checkout_price,
                fee_ship=0,
                total_checkout_price=total_checkout_price
            )
        )