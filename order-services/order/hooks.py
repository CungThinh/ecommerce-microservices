from sqlalchemy import event
from order.models import Order
from order.utils import create_order_tracking_num

def generate_tracking_number(mapper, connection, target):
    tracking_number = create_order_tracking_num(target.id)
    connection.execute(
        target.__table__.update()
        .where(target.__table__.c.id == target.id)
        .values(order_tracking_number=tracking_number)
    )

def register_event_listeners():
    event.listen(Order, "after_insert", generate_tracking_number)
