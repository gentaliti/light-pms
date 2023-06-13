import {useNavigate, useParams, useSearchParams} from "react-router-dom";
import axios from "axios";
import {useEffect, useState} from "react";
import DatePicker from "react-datepicker";
import "react-datepicker/dist/react-datepicker.css";
import dateFormat from "dateformat";
import {API_URL, DATE_PATTERN} from "../constants";
import Button from "../components/button/Button";

export default function CreateBooking() {
    const defaultBookingType = 'RESERVATION';
    const {propertyId} = useParams();
    const navigate = useNavigate();


    const [startDate, setStartDate] = useState(null);
    const [endDate, setEndDate] = useState(null);
    const [bookingType, setBookingType] = useState(defaultBookingType);
    const [error, setError] = useState(null);

    const [searchParams, setSearchParams] = useSearchParams();
    const bookingId = searchParams.get("bookingId");

    const fetchBooking = async () => {
        if (!bookingId) {
            console.log('here');
            setStartDate(null);
            setEndDate(null);
            setBookingType(defaultBookingType);
            return;
        }
        const res = await axios.get(`${API_URL}/booking/${bookingId}`);

        setStartDate(Date.parse(res.data.startDate));
        setEndDate(Date.parse(res.data.endDate));
        setBookingType(res.data.type);
    }

    useEffect(() => {
        fetchBooking();
    }, []);


    const onSubmit = async (event) => {
        event.preventDefault();

        await axios.post(`${API_URL}/booking`, {
            startDate: dateFormat(startDate, DATE_PATTERN),
            endDate: dateFormat(endDate, DATE_PATTERN),
            type: bookingType,
            propertyId: propertyId
        }).then(res => {
            navigate(`/properties/${propertyId}/bookings`);
        }).catch(error => {
            console.log(error);
            setError(error.response.data.message);
        });

    }

    const createBookingForm = <div className='create-booking-form'>

        <form onSubmit={onSubmit} className="form">
            <div className="form-group">
                <div className="label">
                    <label>Start date</label>
                </div>
                <div className="value">
                    <DatePicker selected={startDate} onChange={(date) => setStartDate(date)} dateFormat={DATE_PATTERN}
                                showTimeSelect/>
                </div>
            </div>
            <div className="form-group">
                <div className="label">
                    <label>Start date</label>
                </div>
                <div className="value">
                    <DatePicker selected={endDate} onChange={(date) => setEndDate(date)} dateFormat={DATE_PATTERN}
                                showTimeSelect/>
                </div>
            </div>
            <div className="form-group">
                <div className="label">
                    <label>Booking Type</label>
                </div>
                <div className="value">
                    <select defaultValue={bookingType} onChange={(e) => setBookingType(e.target.value)}>
                        <option value="RESERVATION">RESERVATION</option>
                        <option value="BLOCK">BLOCK</option>
                    </select>

                </div>
            </div>
            <Button primary>Submit</Button>
        </form>

    </div>

    const header = bookingId ? <h1>{`Rebooking a new booking from id: ${bookingId}`}</h1> : <h1>Create new booking</h1>
    const errorMsg = error ? <p>{error}</p> : '';

    return <div className='page container'>
        {header}
        {createBookingForm}

        {errorMsg}
    </div>
}