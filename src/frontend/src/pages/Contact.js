import React, {useEffect, useState} from 'react';
import {sendEmail} from "../client";
import '../../node_modules/bootstrap/dist/css/bootstrap.min.css';
import {errorNotification, successNotification} from "../notifications/Notifications";
import {useNavigate} from "react-router-dom";

export const Contact = () => {
    const [formStatus, setFormStatus] = React.useState('Send');
    const [disabled, isDisabled] = useState(false)
    const jwtToken = localStorage.getItem("jwt");
    const navigate = useNavigate();
    const onClick = () => {
        isDisabled(true);
    };

    const onSubmit = (e) => {
        e.preventDefault();
        setFormStatus('Thank YOU :)');
        const {name, title, message} = e.target.elements;
        let conFom = {
            name: name.value,
            title: title.value,
            message: message.value,
        };
        onClick();
        console.log(conFom);
        sendEmail(conFom)
            .then(() => {
                console.log(conFom);
                successNotification("mail successfully send")
            })
            .catch(err => {
                err.response.json().then(res => {
                    if (!localStorage.getItem("jwt")) {
                        errorNotification("Login to send email", res.status)
                        navigate('/login')
                    } else {
                        errorNotification(res.message, res.status)
                        navigate('/login')
                        localStorage.clear();
                    }
                })
            });
    };

    return (
        <div className="site-container">
            <div className="row justify-content-center">
                <div className="col-md-5 mx-auto">
                    <h2 className="mb-3">Contact Me</h2>
                    <form onSubmit={onSubmit} className="w-100">
                        <div className="mb-3">
                            <label className="form-label" htmlFor="name">
                                Name
                            </label>
                            <input className="form-control" type="text" id="name" required/>
                        </div>
                        <div className="mb-3">
                            <label className="form-label" htmlFor="title">
                                Title
                            </label>
                            <input className="form-control" type="text" id="title" required/>
                        </div>
                        <div className="mb-3">
                            <label className="form-label" htmlFor="message">
                                Message
                            </label>
                            <textarea className="form-control" id="message" required/>
                        </div>
                        <button className="btn btn-danger" type="submit" disabled={disabled}>
                            {formStatus}
                        </button>
                    </form>
                </div>
            </div>
        </div>
    );
};

export default Contact;