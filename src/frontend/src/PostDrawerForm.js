import {Drawer, Input, Col, Form, Row, Button,} from 'antd';
import {addNewPost, updatePost} from "./client";
import {useEffect} from "react";
import {successNotification,errorNotification} from "./Notifications";

function PostDrawerForm({showDrawer, setShowDrawer, isEditing, editingPost, fetchPosts}) {

    const onClose = () => {
        setShowDrawer(false)
    };
    const [form] = Form.useForm();

    const onFinish = post => {
        if (isEditing) {
            console.log(JSON.stringify(post, null, 2));
            updatePost(editingPost.id, post)
                .then(() => {
                    fetchPosts();
                    form.resetFields();
                    successNotification("Post successfully edited")
                    console.log(post);
                }).catch(err => {
                    console.log(err);
                    err.response.json().then(res => {
                        console.log(res)
                        errorNotification("There was an issue", res.status)
                    })
                });
        } else {
            console.log(JSON.stringify(post, null, 2));
            addNewPost(post)
                .then(() => {
                    fetchPosts();
                    form.resetFields();
                    successNotification("Post successfully added")
                    console.log(post);
                })
                .catch(err => {
                    console.log(err);
                    err.response.json().then(res => {
                        console.log(res)
                        errorNotification("There was an issue", res.status)
                    })
                });
        }
    };
    const onFinishFailed = errorInfo => {
        alert(JSON.stringify(errorInfo, null, 2));
    };

    useEffect(() => {
        if (isEditing && editingPost) {
            form.setFieldsValue({
                title: editingPost.title,
                post: editingPost.content,
            });
        }
    }, [isEditing, editingPost, form]);

    return <Drawer
        title={isEditing ? "Edit post" : "Create new post"}
        width={720}
        onClose={onClose}
        visible={showDrawer}
        bodyStyle={{paddingBottom: 80}}
        footer={
            <div
                style={{
                    textAlign: 'right',
                }}
            >
                <Button onClick={onClose} style={{marginRight: 8}}>
                    Cancel
                </Button>
            </div>
        }
    >
        <Form
            form={form}
            layout="vertical"
            onFinish={onFinish}
            onFinishFailed={onFinishFailed}
            initialValues={{
                title: "",
                post: ""
            }}
            hideRequiredMark
        >
            <Row gutter={16}>
                <Col span={12}>
                    <Form.Item
                        name="title"
                        label="Title"
                        rules={[{required: true, message: 'here goes title'}]}
                    >
                        <Input placeholder="here goes title"/>
                    </Form.Item>
                </Col>
            </Row>
            <Row gutter={16}>
                <Col span={12}>
                    <Form.Item
                        name="post"
                        label="Description"
                        rules={[
                            {
                                required: true,
                                message: 'post body',
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
}

export default PostDrawerForm;