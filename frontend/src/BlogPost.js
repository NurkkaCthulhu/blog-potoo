import React, {Component} from "react";
import { Link } from "react-router-dom";
import './css/BlogPost_style.css';

class BlogPost extends Component {

    constructor(props) {
        super(props);

        var id = this.props.id;
        if (this.props.match === undefined) {
            id  = props.id;
        } else {
            id  = this.props.match.params.id;
        }
        let modifyUrl = '/blogposts/modifypost/' + id;
        this.deletePost = this.deletePost.bind(this);
        this.listOfTags = this.listOfTags.bind(this);
        this.makeSeen = this.makeSeen.bind(this);

        let seenID = 'seen' + id;
        let seen = localStorage.getItem(seenID);

        this.state = {
            id: id
            , author: ''
            , title: ''
            , content: ''
            , postDate: '0000-01-01'
            , postTime: '00:00:00'
            , tags: []
            , postUrl: ''
            , modifyUrl: modifyUrl
            , seen: seen
            , seenID: seenID
        }
    }

    componentDidMount() {
        this.setState({isFetching: true});
        fetch('/api/blogposts/' + this.state.id).then((httpResponse) => httpResponse.json())
            .then((blogpost) => {
                let postUrl = '/blogposts/' + this.state.id;
                this.setState({
                    author: blogpost.author
                    , title: blogpost.title
                    , content: blogpost.content
                    , postDate: blogpost.dateOfCreation
                    , postTime: blogpost.timeOfCreation
                    , tags: blogpost.tags
                    , postUrl: postUrl
                    , isFetching: false
                });
                //this.setState({blogpost: blogObject});
            }
        );
    }

    makeSeen() {
        if(localStorage.getItem(this.state.seenID) === 'true') {
            localStorage.setItem(this.state.seenID, 'false');
        } else {
            localStorage.setItem(this.state.seenID, 'true');
        }

        let seed = localStorage.getItem(this.state.seenID);
        this.setState({seen: seed});
    }

    listOfTags() {
        let tagString = '';

        for (let tagObj of this.state.tags) {
            console.log(tagObj);
            tagString = tagString + '#' + tagObj.tagName + ' ';
        }

        return tagString;
    }

    async deletePost() {

        await fetch('/api/blogposts/' + this.state.id, {
            method: 'DELETE',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            }
        }).then(() => {
            //this.props.updateLoader();
        });
    }

    render() {
        let seenBool = false;
        if(localStorage.getItem(this.state.seenID) === 'true') {
            seenBool = true;
        }
        if(this.state.isFetching || this.state.isFetching === undefined) {
            return <p className="loading">Loading post.....</p>;
        }
        return (
                <div className = "container">

                    <div className="postheader"><Link to={this.state.postUrl}><h1>{this.state.title} </h1></Link>
                    </div>
                    <div className="postIcons">
                        <button className="deletebutton" onClick={this.deletePost}><i className='fas fa-times'></i></button>
                        <Link to={this.state.modifyUrl}><button className="modifybutton"><i className='fas fa-pen'></i></button></Link>
                        {seenBool ? <i className='far fa-eye'  onClick={this.makeSeen}></i> :
                            <i className='far fa-eye-slash'  onClick={this.makeSeen}></i>}

                    </div>
                <h3>{this.state.author}</h3>
                <p>Posted: {this.state.postDate} at {this.state.postTime.substring(0,5)}</p>
                <p>{this.state.content}</p>
                <p className = "tagsOfPosts">{this.listOfTags()}</p>
            </div>
        );
    }
}

export default BlogPost;
