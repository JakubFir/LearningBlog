import React, {useState, useEffect} from 'react'
import {Layout, Menu, List, Button, Empty, Popconfirm} from 'antd';
import PostDrawerForm from "../drawers/PostDrawerForm";
import CommentDrawerForm from "../drawers/CommentDrawerForm";
import '../App.css';
import {FileOutlined} from "@ant-design/icons";
import Comments from "./Comments";
import {errorNotification, infoNotification, successNotification} from "../notifications/Notifications";
import ModerateDrawerForm from "../drawers/ModerateDrawerForm";
import {useNavigate} from "react-router-dom";
import {getTranslatedPosts, translatePost} from "../clients/translation";
import {deletePost, getAllPost} from "../clients/clientPost";

const {Content, Footer, Sider} = Layout;

function BlogPosts() {
    const navigate = useNavigate();
    const [posts, setPosts] = useState([]);
    const [collapsed, setCollapsed] = useState(true)
    const [open, setOpen] = useState(false);
    const [post, setPost] = useState(null);
    const [showDrawer, setShowDrawer] = useState(false)
    const [showModerateDrawer, setModerateDrawer] = useState(false);
    const [isEditing, setIsEditing] = useState(false);
    const [isAdmin, setIsAdmin] = useState(null)
    const [editingPost, setEditingPost] = useState(null);
    const jwtToken = localStorage.getItem("jwt");
    const [isLoggedIn, setIsLoggedIn] = useState(false);

    const handleEdit = (post) => {
        setEditingPost(post);
        setIsEditing(true);
        setShowDrawer(true);
    }
    const handleAddingComment = (post) => {
        setPost(post)
        setOpen(true);
    }

    const translate = (content, postId, callbacck) => {
        console.log(content)
        translatePost(content, postId)
            .then(() => {
                successNotification("Post translated sucesfully")
                callbacck()
            }).catch(err => {
            console.log(err)
            err.response.json().then(res => {
                errorNotification("There was an issue", res.status)
            })
        })
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

    const fetchTranslations = () => {
        getTranslatedPosts()
            .then(() => {
                infoNotification("posts restored to original")
                fetchPosts()
            })
    }
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
        fetchTranslations()
        if (!jwtToken) {
            setIsLoggedIn(false)
        } else {
            setIsLoggedIn(true);
            const decodedToken = JSON.parse(atob(jwtToken.split('.')[1]));
            const isAdmin = decodedToken.role
            console.log(isAdmin)
            setIsAdmin(isAdmin);
        }
    }, []);

    const data = posts.map(post => ({
        title: post.title,
        description: post.dateOfPublishing,
        id: post.postId,
        content: post.post,
        translatedPost: post.translation,
        isTranslated: post.translated
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
                <ModerateDrawerForm
                    showModerateDrawer={showModerateDrawer}
                    setModerateDrawer={setModerateDrawer}
                    post={post}
                />
                <Empty/>
            </>
        }
        return <>
            <CommentDrawerForm
                open={open}
                setOpen={setOpen}
                post={post}
            />
            <ModerateDrawerForm
                showModerateDrawer={showModerateDrawer}
                setModerateDrawer={setModerateDrawer}
                post={post}

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
                                    {isAdmin === "ADMIN" && (
                                        <>
                                            <Popconfirm title={'Are You sure you want to delete this post'}
                                                        onConfirm={() => removePost(post.id, fetchPosts)}
                                                        okText='Yes'
                                                        cancelText='No'>
                                                <Button type="primary" shape="round">Delete</Button>
                                            </Popconfirm>
                                            <Button type="primary" shape="round" onClick={() => {
                                                handleEdit(post)
                                            }}>Edit</Button>
                                        </>
                                    )}
                                    {!isLoggedIn && (
                                        <Popconfirm
                                            title={'You are not logged in. Do you want to add anonymous comment that need to be moderated?'}
                                            onConfirm={() => handleAddingComment(post)}
                                            onCancel={() => navigate('/login')}
                                            okText='Yes'
                                            cancelText='Login'>
                                            <Button type="primary" shape="round">
                                                Add Comment
                                            </Button>
                                        </Popconfirm>
                                    )}
                                    {isLoggedIn && (
                                        <Button type="primary" shape="round" onClick={() => {
                                            handleAddingComment(post)
                                        }}>Add Comment
                                        </Button>
                                    )}
                                </div>
                            </div>
                        }
                    >
                        <List.Item.Meta
                            avatar={post.id}
                            title={<a href={post.href}>{post.title}</a>}
                            description={post.description}
                        />
                        <div style={{bottom: "-10px", display: "flex"}}>
                            {!post.isTranslated && (
                                <Button
                                    type="primary"
                                    shape="round"
                                    onClick={() => {
                                        translate(post.content, post.id, fetchPosts);
                                    }}
                                >
                                    Translate
                                </Button>
                            )}
                            {post.isTranslated && (
                                <Button
                                    type="primary"
                                    shape="round"
                                    onClick={() => fetchTranslations()}
                                >
                                    Undo Translations
                                </Button>
                            )}
                        </div>
                        <div>
                            {post.isTranslated ? post.translatedPost : post.content}
                        </div>
                        <Comments postId={post.id}/>
                    </List.Item>
                )}

            />
        </>
    }
    return <Layout style={{minHeight: '100vh'}}>
        {isAdmin === "ADMIN" && (
            <Sider collapsible collapsed={collapsed}
                   onCollapse={setCollapsed}>
                <div className="logo"/>
                <Menu theme="dark" mode="inline">
                    <Menu.Item key="1" icon={<FileOutlined/>}>
                        <Button
                            onClick={() => setShowDrawer(!showDrawer)}
                            type="primary" shape="round">
                            Add new post
                        </Button>
                    </Menu.Item>
                    <Menu.Item key="2" icon={<FileOutlined/>}>
                        <Button
                            onClick={() => setModerateDrawer(!showModerateDrawer)}
                            type="primary" shape="round">
                            New Comments
                        </Button>
                    </Menu.Item>
                </Menu>
            </Sider>
        )}
        <Layout className="site-layout">
            <Content className="content-container" style={{margin: '0 16px'}}>
                {renderPosts()}
            </Content>
            <Footer style={{textAlign: 'center'}}>:)</Footer>
        </Layout>
    </Layout>
}

export default BlogPosts;
