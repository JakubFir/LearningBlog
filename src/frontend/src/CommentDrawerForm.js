import {Button, Col, Drawer, Form, Input, Row,} from 'antd';
import {addCommentToPost} from "./client";
import {successNotification, errorNotification} from "./Notifications";

function CommentDrawerForm({open, setOpen, postId}) {
    const [form] = Form.useForm();

    const onFinish = comment => {
        addCommentToPost(postId, comment)
            .then(() => {
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
                hideRequiredMark
            >
                <Row gutter={16}>
                    <Col span={12}>
                        <Form.Item
                            name="username"
                            label="Username"
                            rules={[{required: true}]}
                        >
                            <Input placeholder="here goes title"/>
                        </Form.Item>
                    </Col>
                </Row>
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
};
export default CommentDrawerForm;