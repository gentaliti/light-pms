import './card.scss';
import cardImg from './../../property.jpg';
import PmsLink from "../link/PmsLink";

export default function Card({content}) {
    return <div className="card">
        <div className="card-header">
            <img src={cardImg} style={{'maxWidth': '100%'}} alt="A white property"/>
        </div>

        <div className="card-content">
            <h1>{content.name}</h1>

            <div className="bookings-link">
                <PmsLink primary to={`/properties/${content.id}/bookings`}>Bookings</PmsLink>
            </div>
        </div>
    </div>
}