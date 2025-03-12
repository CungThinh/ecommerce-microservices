import httpx
from fastapi import HTTPException
from fastapi.encoders import jsonable_encoder

from checkout.schemas import CheckOutRequest, ValidateForCheckOutResponse, CheckOutResponse, CheckOutOrder
from config import Config


class CheckOutService:
    def __init__(self):
        self.product_services_url = Config.PRODUCT_SERVICES_URL

    async def create_checkout(self, checkout_request: CheckOutRequest):
        # Assume user is valid
        async with httpx.AsyncClient() as client:
            try:
                payload = jsonable_encoder({
                    "cartId": checkout_request.cart_id,
                    "cartProducts": [{
                        "productId": product.product_id,
                        "quantity": product.quantity
                    } for product in checkout_request.cart_products]
                })
                response = await client.post(f"{self.product_services_url}/validation/checkout", json=payload)
                data = ValidateForCheckOutResponse(**response.json().get("data", {}))
                total_checkout_price = sum(product.price * product.quantity for product in data.cart_products)

                return CheckOutResponse(
                    cart_products = data.cart_products,
                    cart_id=data.cart_id,
                    user_id = checkout_request.user_id,
                    checkout_order= CheckOutOrder(
                        total_price=total_checkout_price,
                        fee_ship=0,
                        total_checkout_price=total_checkout_price)
                )
            except httpx.HTTPStatusError as e:
                error_data = e.response.json()
                raise HTTPException(
                    status_code=e.response.status_code,
                    detail={
                        "code": error_data.get("code", 0),
                        "message": error_data.get("message", "Unknown error"),
                    }
                )