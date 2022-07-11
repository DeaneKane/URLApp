import React, { useEffect, useState } from 'react';
import axios from 'axios';

const App = () => {

    const [url, setUrl] = useState();
    const [shortUrl, setShortUrl] = useState(null);

    useEffect(() => {
        axios.get('http://localhost:8080/').then(response => {
            console.log(response.data);
        })
    }, [shortUrl]);

    //defining onChangeHandler function
    const onChangeHandler = (e) => {
        setUrl(e.target.value);
        console.log(url);
    }

    //defining onSubmitHandler function
    const onSubmitHandler = (e) => {
      console.log('hello', e);
        e.preventDefault();

        let formData = {
            "originalAddress": url
        }

        axios.post('http://localhost:8080/address/', formData, {
            headers: {
                'Content-Type': 'application/json',
            }
        }).then(response => {
            console.log(response.data);
            setShortUrl(`https://localhost:8080/${response.data.shortAddress}`);
        })
    }

    return (
        <>
            <form onSubmit={onSubmitHandler}>
                <input required type="URL" onChange={onChangeHandler} placeholder="Enter your web address here" name="" id="" />
                <button>Submit</button>
            </form>
            {
                shortUrl ?
                    <>
                        <div className="surl-container">
                            <span style={{ fontSize: '.8rem', marginTop: '0rem', marginBottom: '.5rem', color: '#5A2989', textTransform: 'uppercase' }}>Shortened Web Address</span>
                            <a href={shortUrl} rel="noreferrer" target="_blank" style={{ fontSize: '1.5rem' }} className="surl"> {shortUrl} </a>
                        </div>
                    </>
                    : null
            }
        </>
    );
}

export default App
