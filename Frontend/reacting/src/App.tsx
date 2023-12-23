import './App.css';
import { useEffect, useState } from 'react';
import Rates from './components/rates/Rates';
const AsyncRates = withAsync(Rates);

const API = 'https://api.coincap.io/v2/rates';

const App = () => {
    const [isLoading, setIsLoading] = useState(true);
    const [data, setData] = useState([]);
    const [error, setError] = useState<string[]>([]);

    useEffect(() => {
        fetch(API)
            .then(res => res.json())
            .then(data => {
                setIsLoading(false);
                setData(data.data);
            })
            .catch(error => {
                setError(error);
            });
    }, []);

    return <>{AsyncRates}</>;
};
export default App;
