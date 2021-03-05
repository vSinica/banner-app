import s from "./EditCategories.module.css";
import React, {Component} from "react";
import $ from "jquery";


export default class EditCategories extends Component {
    constructor(props) {
        super(props);
        this.state = {
            categoryName: "",
            categoryReqId: "",
            currentCategoryId: null,
            data: null,
            isCreatingNewCategory: false,
            currentSearch: "",
            responseFromServer: null
        };
        this.OnClickSaveCategory =  this.OnClickSaveCategory.bind(this);
    };

    OnChangeSearch(e){
        e.preventDefault();
        this.state.currentSearch = e.target.value;
        this.forceUpdate();
        this.render();
    }

    onClickDeleteCategory(e){
        e.preventDefault();
        if(this.state.categoryName==null || this.state.categoryReqId==null ||
            this.state.categoryName==="" || this.state.categoryReqId===""){
            alert("Вы ввели некорректные данные!!!");
            return;
        }

        $.ajax({
            type: 'POST',
            url: 'http://localhost:8080/DeleteCategory',
            contentType: 'application/json; charset=utf-8',
            data:  JSON.stringify({"idCategory": this.state.currentCategoryId}),
            dataType: 'json',
            async: true,
            cache: false,
            success: function(data) {
                console.log("delete category query is success" + data);
                this.setState({
                    isLoaded: true,
                });
                if (data != null) {
                    this.state.responseFromServer = data;

                }

            }.bind(this),
            error: function(xhr, status, err) {
                console.error(this.props.url, status, err.toString());

            }.bind(this)
        });
        this.componentDidMount();
        this.forceUpdate();
        this.render();
    }

    OnClickSaveCategory(e) {
        e.preventDefault();
        if(this.state.categoryName==null || this.state.categoryReqId==null ||
            this.state.categoryName==="" || this.state.categoryReqId===""){
            alert("Вы ввели некорректные данные!!!");
            return;
        }

        let category =
            {
                category_name: this.state.categoryName,
                categoryReqId: this.state.categoryReqId,
                idCategory: this.state.currentCategoryId
            };

        if (this.state.isCreatingNewCategory) {
            $.ajax({
                type: 'POST',
                url: 'http://localhost:8080/AddCategory',
                contentType: 'application/json; charset=utf-8',
                data: JSON.stringify(category),
                dataType: 'json',
                async: true,
                cache: false,
                success: function (data) {
                    console.log("add category query is success" + data);
                    this.setState({
                        isLoaded: true
                    });
                    if (data != null) {
                        this.state.responseFromServer = data;
                    }
                }.bind(this),
                error: function (xhr, status, err) {
                    this.componentDidMount();
                    console.error(this.props.url, status, err.toString());
                }.bind(this)
            });
            this.state.isCreatingNewCategory = false;
        }
        else{
            $.ajax({
                type: 'POST',
                url: 'http://localhost:8080/UpdateCategory',
                contentType: 'application/json; charset=utf-8',
                data: JSON.stringify(category),
                dataType: 'json',
                async: true,
                cache: false,
                success: function (data) {
                    console.log("update category query is success" + data);
                    this.setState({
                        isLoaded: true
                    });
                    if (data != null) {
                        this.state.responseFromServer = data;
                    }
                }.bind(this),
                error: function (xhr, status, err) {
                    this.componentDidMount();
                    console.error(this.props.url, status, err.toString());
                }.bind(this)
            });
        }
        this.componentDidMount();
    }

    OnClickCreateNewCategory = () => {
        this.state.responseFromServer = null;
        this.state.isCreatingNewCategory = true;
        this.state.categoryName = "";
        this.state.categoryReqId = "";
        this.forceUpdate();
    }

    componentDidMount(){
        this.GetAllCategories();
    }

    GetAllCategories = () => {
        $.ajax({
            type: 'POST',
            url: 'http://localhost:8080//GetCategories',
            contentType: 'application/json; charset=utf-8',
            dataType: 'json',
            async: true,
            cache: false,
            success: function(data) {
                this.setState({
                    data: data,
                    isLoaded: true
                });

                this.state.isCreatingNewCategory = true;
                data.map(item => (
                    (!item.deleted) ?
                        this.state.isCreatingNewCategory=false
                        : null
                ));
            }.bind(this),
            error: function(xhr, status, err) {
                console.error(this.props.url, status, err.toString());
            }.bind(this)
        });
    }

    saveToStateNameReqId = (id) => {
        this.state.data.map(item => {
            if (item.id === id)
                this.state.categoryName = item.name;
        });

        this.state.data.map(item => {
            if (item.id === id)
                this.state.categoryReqId = item.reqName;
        });
    }

    getClassforLink = (id) => {
        if(this.state.currentCategoryId==null)
        {
            this.state.currentCategoryId = id;
            this.saveToStateNameReqId(id);
            return s.activeLink;
        }
        if (id == this.state.currentCategoryId) {
          ////  console.log(id +" this id is active link");
            this.saveToStateNameReqId(id);
            return s.activeLink;
        }
    }

    setNewCurrentCategory = (id) => {
        this.state.responseFromServer = null;
        this.state.isCreatingNewCategory = false;
        this.state.currentCategoryId = id;
        this.saveToStateNameReqId(id);
        this.componentDidMount();
    }

    render() {
        const {error, isLoaded, data} = this.state;

        if (error) {
            return <p>Error</p>
        } else if (!isLoaded) {
            return <p> Loading... </p>
        } else {
            return (
                <div className = {s.editcategories}>
                    <div className = {s.nav}>
                        <p className={s.categoryLabel}>Categories:</p>

                        <input onChange={(e) => {this.OnChangeSearch(e)}}
                               defaultValue={this.state.currentSearch}
                               key={this.state.currentSearch}
                               placeholder="Поиск"
                               autoFocus
                               className={s.search}/>

                        {data.map(item => (
                            (!item.deleted && (item.name.toLowerCase().includes(this.state.currentSearch.toLowerCase()) || this.state.currentSearch=="")) ? (
                            <div>
                                <p onClick={()=>this.setNewCurrentCategory(item.id)}
                                   className={!this.state.isCreatingNewCategory ? this.getClassforLink(item.id):null}>{item.name}</p>
                            </div>
                            ):null
                        ))}
                        {this.state.isCreatingNewCategory ?
                            (
                            <div>
                                <p className={s.activeLink}>New category</p>
                            </div>
                            )
                            : null
                        }



                        <button className={s.button}
                                onClick={this.OnClickCreateNewCategory}>
                                Create new category
                        </button>

                    </div>

                        <form className={s.formclass}>
                            {this.state.isCreatingNewCategory ?(
                                    <div className={s.inputHeading}>
                                        <p>Creating new category</p>
                                    </div>
                                )
                                :
                                <div className={s.inputHeading}>
                                    <p>{this.state.categoryName} ID: {this.state.currentCategoryId}</p>
                                </div>
                            }

                            <div className={s.inputBlock}>

                                <div>
                                <span>name</span> <input onChange={(e)=>{this.state.categoryName = e.target.value}}
                                            defaultValue={this.state.categoryName}
                                            key={this.state.categoryName}
                                            type="text"/>
                                </div>
                                <div>
                                Request id <input onChange={(e)=>{this.state.categoryReqId = e.target.value}}
                                                  defaultValue={this.state.categoryReqId}
                                                  key={this.state.categoryReqId}
                                                  type="text"/>
                                </div>
                            </div>

                            {this.state.responseFromServer ?
                                <div className={s.notification}>
                                    <p>{this.state.responseFromServer} </p>
                                </div>
                                : null
                            }


                            <div className={s.buttonBlock}>
                                <p onClick={(e) => {this.OnClickSaveCategory(e)}}
                                        className={s.savebutton} ><p>save</p></p>
                                <p onClick={(e)=>{this.onClickDeleteCategory(e)}}
                                        className={s.deletebutton} ><p>delete</p></p>
                            </div>
                        </form>
                    </div>
            );
        }
    }


}
