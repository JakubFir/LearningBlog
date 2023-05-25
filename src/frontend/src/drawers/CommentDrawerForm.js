import {Button, Col, Drawer, Form, Input, Row,} from 'antd';
import {sendAnonymousCommentToKafka} from "../client";
import {successNotification, errorNotification} from "../notifications/Notifications";
import {useEffect, useState} from "react";
import {addCommentToPost} from "../clients/clientComment";
import {useNavigate} from "react-router-dom";

function CommentDrawerForm({open, setOpen, post}) {
    const navigate = useNavigate();
    const [form] = Form.useForm();
    const jwtToken = localStorage.getItem("jwt");
    const [userId, setUserId] = useState(null)


    useEffect(() => {
        if (!jwtToken) {
        } else {
            const jwtToken = localStorage.getItem("jwt");
            const decodedToken = JSON.parse(atob(jwtToken.split('.')[1]));
            setUserId(decodedToken.userId);
        }

    }, []);

    const onFinish = comment => {
        if (userId === null) {
            sendAnonymousCommentToKafka(post.id, comment)
                .then(() => {
                    successNotification("comment send to approve")
                }).catch(err => {
                err.response.json().then(res => {
                    errorNotification(res.message, res.status)
                })
            })
        } else {
            addCommentToPost(post.id, comment, userId)
                .then(() => {
                    form.resetFields();
                    successNotification("Comment added to post")
                }).catch(err => {
                err.response.json().then(res => {
                    const resString = JSON.stringify(res);
                    errorNotification(res.message, res.status)
                    if (resString.includes("JWT")) {
                        localStorage.clear();
                        navigate('/login')
                    }
                })
            });
        }
    }

    const onClose = () => {
        setOpen(false);
    };
    return (
        <Drawer
            title="Basic Drawer"
            placement="right"
            closable={false}
            width={400}
            visible={open}
            open={open}
            getContainer={false}
        >
            <Form
                form={form}
                layout="vertical"
                onFinish={onFinish}
                initialValues={{
                    title: "",
                    post: ""
                }}
            >
                <Row gutter={16}>
                    <Col span={12}>
                        <Form.Item
                            name="commentBody"
                            label="Description"
                            rules={[
                                {
                                    required: true,
                                },
                            ]}
                        >
                            <Input.TextArea rows={20} placeholder="post body"/>
                        </Form.Item>
                    </Col>
                </Row>
                <Row>
                    <Col span={12}>
                        <Form.Item>
                            <Button
                                onClick={onClose}
                                type="primary" htmlType="submit">
                                Submit
                            </Button>
                        </Form.Item>
                    </Col>
                </Row>
            </Form>
        </Drawer>
    );
}

export default CommentDrawerForm;