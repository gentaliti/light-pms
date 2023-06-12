import {Link, useNavigate, useParams} from "react-router-dom";
import {useEffect, useState} from "react";
import axios from "axios";
import SortableTable from "../components/table/SortableTable";
import PmsLink from "../components/link/PmsLink";
import Button from "../components/button/Button";
import {API_URL} from "../constants";

export default function BookingPage() {

    const navigate = useNavigate();
    const {propertyId} = useParams();

    const [bookings, setBookings] = useState([]);

    const fetchBookings = async () => {
        const res = await axios.get(`${API_URL}/booking/property/${propertyId}`);
        setBookings(res.data);
    }

    const onBookingCancel = (booking) => {
        axios.put(`${API_URL}/booking`, {
            ...booking,
            status: 'CANCELED'
        }).then(response => {
            fetchBookings();
        });
    }

    const onBookingDelete = (booking) => {
        axios.delete(`${API_URL}/booking/${booking.id}`).then(response => {
            fetchBookings();
        });
    }
    const onRebook = (booking) => {
        navigate(`/properties/${propertyId}/create-booking?bookingId=${booking.id}`);
    }

    const config = [
        {
            label: 'Start Date',
            render: (row) => row.startDate,
            sortValue: (fruit) => fruit.startDate
        },
        {
            label: 'End Date',
            render: (row) => row.endDate,
            sortValue: (row) => row.endDate
        },
        {
            label: 'Type',
            render: (row) => row.type,
            sortValue: (row) => row.type
        },
        {
            label: 'Status',
            render: (row) => row.status,
            sortValue: (row) => row.status
        },
        {
            label: 'Modify',
            render: (row) => <div className="flex space-around">
                <PmsLink primary
                         onClick={() => navigate(`/properties/${propertyId}/update-booking/${row.id}`)}>Edit</PmsLink>
                <Button warning onClick={() => onBookingCancel(row)}>Cancel</Button>
                <Button danger onClick={() => onBookingDelete(row)}>Delete</Button>
                <Button secondary onClick={() => onRebook(row)}>Rebook</Button>
            </div>
        }
    ];
    const keyFn = (row) => {
        return row.id
    }

    useEffect(() => {
        fetchBookings();
    }, []);

    const content = bookings.length > 0 ?
        <SortableTable className="width-full" data={bookings} config={config} keyFn={keyFn}/> :
        <h3>No bookings yet...</h3>

    return <div className="page booking-page">
        <div className="container">

            <div className="booking-header m-bottom-24 flex space-between">
                <h1>Displaying bookings for property with id: {propertyId}</h1>
                <PmsLink secondary to={`/properties/${propertyId}/create-booking`}>Add a booking</PmsLink>
            </div>

            {content}

        </div>
    </div>
}