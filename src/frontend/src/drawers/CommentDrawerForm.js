import {Button, Col, Drawer, Form, Input, Row,} from 'antd';
import {sendAnonymousCommentToKafka} from "../client";
import {successNotification, errorNotification} from "../notifications/Notifications";
import {useEffect, useState} from "react";
import {addCommentToPost} from "../clients/clientComment";

function CommentDrawerForm({open, setOpen, post}) {

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
                    successNotification("comment send to aprove")
                }).catch(err => {
                console.log(err)
                err.response.json().then(res => {
                    console.log(res)
                    errorNotification("There was an issue", res.status)
                })
            })
        } else {
            addCommentToPost(post.id, comment, userId)
                .then(() => {
                    console.log(comment)
                    form.resetFields();
                    successNotification("Comment added to post")
                    console.log(comment);
                }).catch(err => {
                console.log(err)
                err.response.json().then(res => {
                    console.log(res)
                    errorNotification("There was an issue", res.status)
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