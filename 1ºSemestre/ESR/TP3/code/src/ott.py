class Node:
    """Every node in the network has a set of common properties."""

    def __init__(self, node_id, incoming_port_id, outgoing_port_id):
        self.node_id = node_id
        self.incoming_port_id = incoming_port_id
        self.outgoing_port_id = outgoing_port_id
        self.nodes = []


class Streamer(Node):
    """Streamer nodes exist to serve the User nodes data. To do so
    they send data through the Relay nodes."""

    def __init__(self, node_id, incoming_port_id, outgoing_port_id):
        super().__init__(node_id, incoming_port_id)


class Relay(Node):
    """Relay nodes serve as a 'relay' between the other actors of the network."""

    def __init__(self, node_id, incoming_port_id, outgoing_port_id):
        super().__init__(node_id, incoming_port_id)


class Client(Node):
    def __init__(self, node_id, incoming_port_id, outgoing_port_id):
        super().__init__(node_id, incoming_port_id)


class Route:
    """Default message between Streamer and Relay methods"""

    def __init__(self, flux, origin, metric, destinies, state):
        self.flux = flux
        self.origin = origin
        self.metric = metric
        self.destinies = destinies
        self.state = state
