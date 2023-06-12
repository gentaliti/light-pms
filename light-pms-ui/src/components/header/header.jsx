import './header.scss'
import {Link} from "react-router-dom";

export default function Header() {
    return <div className="header">

        <div className="container">
            <div className="header-content">
                <Link to='/'>
                    <h1> Light PMS</h1>
                </Link>
            </div>
        </div>
    </div>
}