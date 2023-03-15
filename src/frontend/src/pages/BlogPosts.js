import React, {useState, useEffect} from 'react'
import {getAllPost, deletePost} from "../client";
import {Layout, Menu, List, Button, Empty, Popconfirm} from 'antd';
import PostDrawerForm from "../PostDrawerForm";
import CommentDrawerForm from "../CommentDrawerForm";
import '../App.css';
import {FileOutlined} from "@ant-design/icons";
import Comments from "./Comments";
import {errorNotification, successNotification} from "../Notifications";

const {Content, Footer, Sider} = Layout;

function BlogPosts() {
    const [posts, setPosts] = useState([]);
    const [collapsed, setCollapsed] = useState(true)
    const [open, setOpen] = useState(false);
    const [postId, setPostId] = useState(null);
    const [showDrawer, setShowDrawer] = useState(false)
    const [isEditing, setIsEditing] = useState(false);
    const [editingPost, setEditingPost] = useState(null);

    const handleEdit = (post) => {
        setEditingPost(post);
        setIsEditing(true);
        setShowDrawer(true);
    }
    const handleAddingComment = (post) => {
        setPostId(post.id)
        setOpen(true);
    }

    const removePost = (postId, callback) => {
        deletePost(postId)
            .then(() => {
                successNotification("Post successfully deleted")
                callback();
            }).catch(err => {
            console.log(err)
            err.response.json().then(res => {
                console.log(res)
                errorNotification("There was an issue", res.status)
            })
        })
    };

    const fetchPosts = () =>
        getAllPost()
            .then(res => res.json())
            .then(data => {
                console.log(data);
                setPosts(data);
            }).catch(err => {
            console.log(err)
            err.response.json().then(res => {
                console.log(res)
                errorNotification("There was an issue", res.status)
            })
        })

    useEffect(() => {
        console.log("component is mounted");
        fetchPosts();
    }, []);

    const data = posts.map(post => ({
        title: post.title,
        description: post.dateOfPublishing,
        id: post.postId,
        content: post.post,
    }));

    const renderPosts = () => {
        if (posts.length <= 0) {
            return <>
                <PostDrawerForm
                    showDrawer={showDrawer}
                    setShowDrawer={setShowDrawer}
                    isEditing={isEditing}
                    editingPost={editingPost}
                    fetchPosts={fetchPosts}
                />

                <Empty/>
            </>
        }
        return <>
            <CommentDrawerForm
                open={open}
                setOpen={setOpen}
                postId={postId}
            />
            <PostDrawerForm
                showDrawer={showDrawer}
                setShowDrawer={setShowDrawer}
                isEditing={isEditing}
                editingPost={editingPost}
                fetchPosts={fetchPosts}
            />
            <List
                itemLayout="vertical"
                size="large"
                pagination={{
                    onChange: (page) => {
                        console.log(page);
                    },
                    pageSize: 4,
                }}
                dataSource={data}
                renderItem={(post) => (
                    <List.Item

                        style={{overflowWrap: "break-word", wordWrap: "break-word", whiteSpace: "pre-wrap"}}
                        key={post.id}
                        actions={[]}
                        extra={
                            <div style={{position: "relative"}}>
                                <img
                                    width={272}
                                    alt="logo"
                                    src="https://gw.alipayobjects.com/zos/rmsportal/mqaQswcyDLcXyDKnZfES.png"
                                />
                                <div style={{bottom: "-10px"}}>
                                    <Popconfirm title={'Are You sure you want to delete this post'}
                                                onConfirm={() => removePost(post.id, fetchPosts)}
                                                okText='Yes'
                                                cancelText='No'>
                                        <Button type="primary" shape="round">Delete</Button>
                                    </Popconfirm>
                                    <Button type="primary" shape="round" onClick={() => {
                                        handleEdit(post)
                                    }}>Edit</Button>
                                    <Button type="primary" shape="round" onClick={() => {
                                        handleAddingComment(post)
                                    }}>Add Comment
                                    </Button>
                                </div>
                            </div>
                        }
                    >
                        <List.Item.Meta
                            avatar={post.id}
                            title={<a href={post.href}>{post.title}</a>}
                            description={post.description}

                        />
                        {post.content}
                        <Comments postId={post.id}/>
                    </List.Item>

                )}

            />
        </>
    }
    return <Layout style={{minHeight: '100vh'}}>
        <Sider collapsible collapsed={collapsed}
               onCollapse={setCollapsed}>
            <div className="logo"/>
            <Menu theme="dark" mode="inline">
                <Menu.Item key="1" icon={<FileOutlined/>}>
                    <Button
                        onClick={() => setShowDrawer(!showDrawer)}
                        type="primary" shape="round">
                        add new post
                    </Button>
                </Menu.Item>
            </Menu>
        </Sider>
        <Layout className="site-layout">
            <Content className="content-container" style={{margin: '0 16px'}}>
                {renderPosts()}
            </Content>
            <Footer style={{textAlign: 'center'}}>:)</Footer>
        </Layout>
    </Layout>
}

export default BlogPosts;
