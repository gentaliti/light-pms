import {Outlet} from "react-router-dom";
import Header from "./components/header/header";
import './styles/general.scss';

function App() {
    return (
        <div className="App">
            <Header></Header>
            <Outlet/>
        </div>
    );
}

export default App;
