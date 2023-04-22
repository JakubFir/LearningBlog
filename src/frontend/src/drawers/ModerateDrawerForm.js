import {Avatar, Button, Drawer, Form, List} from "antd";
import {useEffect, useState} from "react";
import {addAnonymousCommentToPost, getAnonymousComment} from "../clients/anonymousComments";
import {errorNotification, successNotification} from "../notifications/Notifications";


function ModerateDrawerForm({showModerateDrawer, setModerateDrawer}) {

    const [form] = Form.useForm();
    const [comments, setComments] = useState([]);

    useEffect(() => {
        if (showModerateDrawer) {
            getCommentsToApprove();
        }
    }, [showModerateDrawer]);

    const approveComment = (postId, comment, commentId) => {
        addAnonymousCommentToPost(postId, comment, commentId)
            .then(() => {
                getCommentsToApprove()
                successNotification("Comment added to post")
            }).catch(err => {
            console.log(err)
            err.response.json().then(res => {
                console.log(res)
                errorNotification("There was an issue", res.status)
            })
        });
    }

    const getCommentsToApprove = () => {
        getAnonymousComment()
            .then(res => res.json())
            .then(data => {
                setComments(data);
            }).catch(data => {
            console.log(data)
        })
    }

    const data = comments.map(comment => ({
        id: comment.commentId,
        commentBody: comment.commentBody,
        postId: comment.postId

    }))

    const onClose = () => {
        setModerateDrawer(false)
    };

    return <Drawer
        title="Basic Drawer"
        placement="right"
        onClose={onClose}
        open={showModerateDrawer}
        visible={showModerateDrawer}
        closable={false}
        width={600}
    >
        <Form
            form={form}>
            <List
                dataSource={data}
                bordered
                renderItem={(item) => (
                    <List.Item
                        key={item.id}
                        actions={[
                            <Button onClick={() => {
                                approveComment(item.postId, item, item.id)
                            }}>
                                Accept
                            </Button>
                        ]}
                    >
                        <List.Item.Meta
                            avatar={
                                <Avatar
                                    src="https://gw.alipayobjects.com/zos/rmsportal/BiazfanxmamNRoxxVxka.png"/>
                            }
                            title={item.id}
                            description={item.commentBody}
                        />
                    </List.Item>
                )}
            />
        </Form>
    </Drawer>
}

export default ModerateDrawerForm;