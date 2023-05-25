import React,{useState} from "react";
import {Button, Form, Input} from "antd";
import {useNavigate} from "react-router-dom";
import {errorNotification, successNotification} from "../notifications/Notifications";
import {authonticateUser, registerUser} from "../clients/auth";

const Register = () => {
    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");
    const navigate = useNavigate();

    function sendLoginRequest() {
        const reqBody = {
            username: username,
            password: password,
        };
        console.log(reqBody)
        authonticateUser(reqBody)
            .then(response => response.json())
            .then(data => {
                localStorage.setItem('jwt', data.token);
                successNotification("Welcome " + username)
                navigate('/posts');
            }).catch(err => {
            errorNotification("Invalid username or password", err.data)
        })

    }

    function sendRegisterRequest() {
        const reqBody = {
            username: username,
            password: password,
        };
        if (!username || !password) {
            errorNotification("Username and password are required");
            return;
        }
        registerUser(reqBody)
            .then(() => {
                successNotification("user succesfully registered")
            })
            .catch(err => {
                errorNotification("username taken", err.data)
            })
    }

    return (
        <>
            <div style={{ backgroundColor: 'white', minHeight: '30vh', display: 'flex', alignItems: 'center', justifyContent: 'center' }}>
            <Form
                name="basic"
                labelCol={{
                    span: 8,
                }}
                wrapperCol={{
                    span: 16,
                }}
                style={{
                    maxWidth: 600,
                }}
                initialValues={{
                    remember: true,
                }}
                autoComplete="off"
            >
                <Form.Item
                    name="username"
                    label="Username"
                    rules={[{required: true, message: 'Please input your username!'}]}
                >
                    <Input value={username} placeholder="here goes title"
                           onChange={(e) => setUsername(e.target.value)}/>
                </Form.Item>

                <Form.Item
                    name="password"
                    label="Password"
                    rules={[{required: true, message:'Please input your password!',}]}
                >
                    <Input value={password} type={"password"} onChange={(e) => setPassword(e.target.value)}/>
                </Form.Item>

                <Form.Item
                    wrapperCol={{
                        offset: 8,
                        span: 16,
                    }}>
                    <div style={{ display: 'flex'}}>
                    <Button
                        onClick={() => sendLoginRequest()}
                        style={{ marginRight: '5px' }}
                        type="primary" htmlType="submit">
                        Login
                    </Button>
                    <Button
                        onClick={() => sendRegisterRequest()}
                        type="primary" htmlType="submit">
                        Register
                    </Button>
                        </div>
                </Form.Item>
            </Form>
            </div>
        </>
    );
}
export default Register;