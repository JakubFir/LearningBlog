import {Header} from "antd/es/layout/layout";
import {Button, Menu} from "antd";
import React from "react";
import {Link} from "react-router-dom";

export function BlogNavi() {
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
                    </div>
                </Menu>
            </Header>
        </>
    )
}