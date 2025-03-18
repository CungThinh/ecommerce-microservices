from typing import List

from fastapi import HTTPException
from fastapi.encoders import jsonable_encoder

from config import Config
import httpx
from order.schemas import ProductReserveResponse, CartProduct
from checkout.schemas import ValidateForCheckOutResponse
from exceptions import ProductOutOfStock


class ProductServiceClient:
    def __init__(self):
        self.base_url = Config.PRODUCT_SERVICES_URL

    async def reserve_products(self, cart_id: str, order_products: List[CartProduct]):
        async with httpx.AsyncClient() as client:
            try:
                payload = jsonable_encoder({
                    "cartId": cart_id,
                    "cartProducts": [{
                        "productId": product.product_id,
                        "quantity": product.quantity
                    } for product in order_products]
                })
                response = await client.post(f"{self.base_url}/products/reserve", json=payload)
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

    async def validate_checkout(self, checkout_request):
        async with httpx.AsyncClient() as client:
            try:
                payload = jsonable_encoder({
                    "cartId": checkout_request.cart_id,
                    "cartProducts": [{
                        "productId": product.product_id,
                        "quantity": product.quantity
                    } for product in checkout_request.cart_products]
                })
                response = await client.post(f"{self.base_url}/validation/checkout", json=payload)
                response.raise_for_status()

                return ValidateForCheckOutResponse(**response.json().get("data", {}))

            except httpx.HTTPStatusError as e:
                error_data = e.response.json()
                raise HTTPException(
                    status_code=e.response.status_code,
                    detail={
                        "code": error_data.get("code", 0),
                        "message": error_data.get("message", "Unknown error"),
                    }
                )