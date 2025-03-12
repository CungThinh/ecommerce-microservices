from datetime import datetime

def create_order_tracking_num(order_id: int) -> str:
    return f"{order_id}{datetime.now().strftime('%d%m%Y%H%M%S')}"