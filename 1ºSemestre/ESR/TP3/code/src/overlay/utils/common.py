class HeaderTypes:
    """
    This enum declares the header every message must have to be valid.
    If a message is received and does not possess one of these then
    the message must be discarded.

    Every header is a unique character.
    """
    HELLO = "H"   # Hello message
    HEARTBEAT = "D"   # Distance to stream data
    REQUEST = "R"   # Request for a stream
    STREAM = "S"   # Stream packet


def packet_factory(id, data, header: HeaderTypes):
    """
    Creates a packet with the given data and header.

    @param id: An ID of the node that send the data.
    @param data: The data of the packet.
    @param header: The header of the packet.
    @return: The packet.
    """
    # depending on the header create the packet
    if header == HeaderTypes.HELLO:
        return header
    if header == HeaderTypes.HEARTBEAT:
        return header + str(data)
    elif header == HeaderTypes.REQUEST:
        return header
    elif header == HeaderTypes.STREAM:
        return header.encode() + len(data).to_bytes(4,byteorder="big") + data
    else:
        raise ValueError("Invalid header")


def unpack_stream(packet):
    """
    Unpacks a packet, returning the rtp packet contained within.
    This method assumes the packet received is a stream packet.

    @param packet: The packet we want to unpack.
    @return: The rtp packet contained inside the given packet.
    """
    # head = packet[0]
    # id = packet[1:17]
    return packet[5:]
    # return (head, id, packet[17:])


def unpack_heartbeat(packet):
    """
    Unpacks an heartbeat packet, returns the integer after the header type
    This method assumes the packet received is a heartbeat packet.

    @param packet: The packet we want to unpack.
    @return: The integer after the header type.
    """
    # head = packet[0]
    return int(packet[1:])
