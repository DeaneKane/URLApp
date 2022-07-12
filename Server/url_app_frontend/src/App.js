import React, { useState } from 'react';
import axios from 'axios';

const LogoViewer = ({logo, alt}) => {

    return (
        <>
            <img src={logo} alt={alt} width="20%" height="20%" />
        </>
    )
}

const UrlViewer = ({ url }) => {
    return (
        <>
            <div className="surl-container">
                <span style={{ fontSize: '.8rem', marginTop: '0rem', marginBottom: '.4rem', color: '#5A2989',}}>Short Web Address</span>
                <a href={url} rel="noreferrer" target="_blank" style={{ fontSize: '1.1rem' }} className="surl"> {url} </a>
            </div>
        </>
    )
}

const App = () => {

    const [url, setUrl] = useState();
    const [shortUrl, setShortUrl] = useState(null);

    const onChangeHandler = (e) => {
        setUrl(e.target.value);
        console.log(url);
    }

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
            //localhost would be changed to a short host in Prod environment
            setShortUrl(`http://localhost:8080/${response.data.shortAddress}`);
        })
    }

    return (
        <>
            <div>
                <LogoViewer alt="Tea Pot" logo="https://freesvg.org/img/1537384961.png" />
            </div>
            <form onSubmit={onSubmitHandler}>
                <input required type="URL" onChange={onChangeHandler} placeholder="Enter your web address here" name="" id="" />
                <button>Submit</button>
            </form>
            {
                shortUrl ?
                    <UrlViewer url={shortUrl} />
                    : null
            }
        </>
    );
}

export default App
