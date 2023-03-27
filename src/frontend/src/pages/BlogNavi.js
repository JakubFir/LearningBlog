import {Header} from "antd/es/layout/layout";
import {Button, Menu} from "antd";
import React, {useEffect, useState} from "react";
import {Link, useNavigate} from "react-router-dom";
import {successNotification} from "../notifications/Notifications";

export function BlogNavi() {
    const [isLoggedIn, setIsLoggedIn] = useState(localStorage.getItem("jwt"));
    const navigate = useNavigate();

    useEffect(() => {
        setIsLoggedIn(localStorage.getItem("jwt"));
    }, [localStorage.getItem("jwt")]);

    function handleLogout() {
        localStorage.removeItem('jwt');
        navigate('/login');
        successNotification("sucesfully logged out")
    }

    return (
        <>
            <Header
                style={{display: "flex", justifyContent: "space-between", alignItems: "center"}}>
                <div className="logo"/>
                <Menu theme="dark" mode="inline">
                    <div className="button-group">
                        <Link to="/">
                            <Button type="primary"
                                    shape="round">
                                Home
                            </Button>
                        </Link>
                        <Link to="/posts">
                            <Button type="primary" shape="round">
                                My Blog
                            </Button>
                        </Link>
                        <Link to="/about-me">
                            <Button type="primary" shape="round"
                            >
                                About me
                            </Button>
                        </Link>
                        <Link to="/contact">
                            <Button type="primary"
                                    shape="round">
                                Contact
                            </Button>
                        </Link>

                        {isLoggedIn ? (
                            <Button type="primary" shape="round" onClick={handleLogout}>
                                Logout
                            </Button>
                        ) : (
                            <Link to="/login">
                                <Button type="primary" shape="round">
                                    Login
                                </Button>
                            </Link>
                        )}
                    </div>
                </Menu>
            </Header>
        </>
    )
}