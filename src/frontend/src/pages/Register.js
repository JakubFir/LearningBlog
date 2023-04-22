import React,{useState} from "react";
import {Button, Form, Input} from "antd";
import {useNavigate} from "react-router-dom";
import {errorNotification, successNotification} from "../notifications/Notifications";
import {authonticateUser, registerUser} from "../clients/auth";

const Register = () => {
    const [form] = Form.useForm();
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
                console.log(data.token)
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
            <Form
                form={form}
                layout="vertical"
                initialValues={{
                    title: "",
                    post: ""
                }}>
                <Form.Item
                    name="username"
                    label="Username"
                    rules={[{required: true, message: 'here goes title'}]}
                >
                    <Input value={username} placeholder="here goes title"
                           onChange={(e) => setUsername(e.target.value)}/>
                </Form.Item>
                <Form.Item
                    name="password"
                    label="Password"
                    rules={[{required: true, message: 'here goes title',}]}
                >
                    <Input value={password} type={"password"} onChange={(e) => setPassword(e.target.value)}/>
                </Form.Item>
                <Form.Item>
                    <Button
                        onClick={() => sendLoginRequest()}
                        type="primary" htmlType="submit">
                        Login
                    </Button>
                    <Button
                        onClick={() => sendRegisterRequest()}
                        type="primary" htmlType="submit">
                        Register
                    </Button>
                </Form.Item>

            </Form>
        </>
    );
}
export default Register;