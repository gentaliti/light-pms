import {useNavigate, useParams} from "react-router-dom";
import axios from "axios";
import {useState} from "react";
import DatePicker from "react-datepicker";
import "react-datepicker/dist/react-datepicker.css";
import dateFormat from "dateformat";
import {API_URL, DATE_PATTERN} from "../constants";
import Button from "../components/button/Button";

export default function CreateBooking() {
    const {propertyId} = useParams();
    const navigate = useNavigate();

    const [startDate, setStartDate] = useState(null);
    const [endDate, setEndDate] = useState(null);
    const [bookingType, setBookingType] = useState('RESERVATION');

    const onSubmit = async (event) => {
        event.preventDefault();

        await axios.post(`${API_URL}/booking`, {
            startDate: dateFormat(startDate, DATE_PATTERN),
            endDate: dateFormat(endDate, DATE_PATTERN),
            type: bookingType,
            propertyId: propertyId
        });

        navigate(`/properties/${propertyId}/bookings`);
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
                    <select onChange={(e) => setBookingType(e.target.value)}>
                        <option value="RESERVATION">RESERVATION</option>
                        <option value="BLOCK">BLOCK</option>
                    </select>

                </div>
            </div>
            <Button primary>Submit</Button>
        </form>

    </div>


    return <div className='page container'>
        <h1>Save booking</h1>
        {createBookingForm}
    </div>
}