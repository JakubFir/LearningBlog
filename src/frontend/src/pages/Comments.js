import {Collapse, Divider} from 'antd';
import {useEffect, useState} from "react";
import {errorNotification} from "../notifications/Notifications";
import {getPostComments} from "../clients/clientPost";

const {Panel} = Collapse;

const App = post => {
    const [comments, setComments] = useState([]);

    useEffect(() => {
        fetchComments(post.postId);
    }, [post.postId])


    const fetchComments = postId =>
        getPostComments(postId)
            .then(res => res.json())
            .then(data => {
                setComments(data);
            }).catch(err => {
            console.log(err)
            err.response.json().then(res => {
                console.log(res)
                errorNotification(res.message, res.status)
            })
        });

    const renderComments = () => {
        return comments.map((comment) => (
            <div key={comment.commentId} className="comment">
                <p>{comment.dateOfPublishing}</p>
                <p>{comment.commentBody}</p>
                <p>By {comment.username}</p>
                <Divider/>
            </div>
        ));
    };

    return (
        <Collapse  defaultActiveKey={['0']} onChange={() => fetchComments(post.postId)} >
            <Panel header="Comments" key="1" extra={"Comments: " + comments.length}>
                {renderComments()}
            </Panel>
        </Collapse>
    );
};

export default App;