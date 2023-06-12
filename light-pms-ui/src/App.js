import {Outlet, useLocation} from "react-router-dom";
import Header from "./components/header/header";
import './styles/general.scss';

function App() {

    const location = useLocation();
    const currentPath = location.pathname;

    return (
        <div className="App">
            <Header></Header>

            <Outlet/>
        </div>
    );
}

export default App;
