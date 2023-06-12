import React from 'react';
import ReactDOM from 'react-dom/client';
import App from './App';
import './styles/general.scss';
import {createHashRouter, RouterProvider} from "react-router-dom";
import ErrorPage from "./routes/error-page";
import PropertyPage from "./routes/property-page";
import BookingPage from "./routes/booking-page";
import CreateBooking from "./routes/create-booking-page";
import UpdateBookingPage from "./routes/update-booking-page";

const router = createHashRouter([
    {
        path: "/",
        element: <App/>,
        errorElement: <ErrorPage/>,
        children: [
            {
                path: "/",
                element: <PropertyPage/>
            },
            {
                path: "/properties/:propertyId/bookings",
                element: <BookingPage/>
            },
            {
                path: "/properties/:propertyId/create-booking",
                element: <CreateBooking/>
            },
            {
                path: "/properties/:propertyId/update-booking/:bookingId",
                element: <UpdateBookingPage/>
            }
        ]
    },
]);

const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(
    <RouterProvider router={router}/>
);
