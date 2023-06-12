import Card from "../components/card/card";
import {useEffect, useState} from "react";
import axios from "axios";
import {API_URL} from "../constants";

export default function PropertyPage() {

    const [properties, setProperties] = useState([]);

    const fetchProperties = async () => {
        const res = await axios.get(`${API_URL}/property`);

        setProperties(res.data);
    }

    useEffect(() => {
        fetchProperties();
    }, []);


    const propertiesList = properties?.map(property => {
        return <Card key={property.id} content={property}/>
    });

    return <div className="page property-page">
        <div className="container">
            <div className="grid grid--3-cols">
                {propertiesList}
            </div>
        </div>
    </div>
}