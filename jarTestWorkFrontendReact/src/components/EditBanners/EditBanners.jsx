import s from "./EditBanners.module.css";
import React, {Component} from "react";
import $ from "jquery";


export default class EditBanners extends Component {
    constructor(props) {
        super(props);
        this.state = {
            bannerName: "",
            price: "",
            currentBannerId: null,
            currentCategory: "",
            bannerText: "",
            data: null,
            isCreatingNewBanner: false,
            allCategoryNames: "",
            responseFromServer: "",
            currentSearch: ""
        };
        this.OnClickSaveBanner =  this.OnClickSaveBanner.bind(this);
    };

    onClickDeleteBanner(e){
        e.preventDefault();
        if(this.state.bannerName==null || this.state.bannerText==null ||
            this.state.bannerName==="" || this.state.bannerText===""){
            alert("Вы ввели некорректные данные!!!");
            return;
        }

        $.ajax({
            type: 'POST',
            url: 'http://localhost:8080/DeleteBanner',
            contentType: 'application/json; charset=utf-8',
            data:  JSON.stringify({"bannerId": this.state.currentBannerId,"category_id": this.state.currentCategory}),
            dataType: 'json',
            async: false,
            cache: false,
            success: function(data) {
                console.log("delete category query is success  " + data);
                this.setState({
                    isLoaded: true
                });
                if (!data == null) {
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

    OnChangeSearch(e){
        e.preventDefault();
        console.log(e.target.value);
        this.state.currentSearch = e.target.value;
        this.forceUpdate();
        this.render();
    }

    OnClickSaveBanner(e) {
        e.preventDefault();

        if(this.state.bannerName==="" || this.state.price==="" ||
            this.state.bannerText==="" ){
            alert("Вы ввели некорректные данные!!!");
            return;
        }

        this.state.price = Number(this.state.price);
        if(!Number.isInteger(this.state.price)){
            alert("Price должно быть числом");
            return;
        }

        if(this.state.currentCategory=="" && this.state.allCategoryNames[0]==null){
            alert("Создайте хотя бы одня категорию!");
            return;
        }

        if(this.state.allCategoryNames != null && this.state.currentCategory=="")
            this.state.currentCategory = this.state.allCategoryNames[0].name;

        let banner =
            {
                id: this.state.currentBannerId,
                bannerName: this.state.bannerName,
                price: this.state.price,
                currentCategory: this.state.currentCategory,
                bannerText: this.state.bannerText
            };

        if (this.state.isCreatingNewBanner) {
            $.ajax({
                type: 'POST',
                url: 'http://localhost:8080/AddBanner',
                contentType: 'application/json; charset=utf-8',
                data: JSON.stringify(banner),
                dataType: 'json',
                async: false,
                cache: false,
                success: function (data) {
                    console.log("add category query is success" + data);
                    this.setState({
                        isLoaded: true
                    });
                    this.state.isCreatingNewBanner = false;
                    if (data != null) {
                        this.state.responseFromServer = data;
                    }
                    this.componentDidMount();
                }.bind(this),
                error: function (xhr, status, err) {
                    this.state.isCreatingNewBanner = false;
                    this.componentDidMount();
                    console.error(this.props.url, status, err.toString());
                }.bind(this)
            });

        }
        else{
            $.ajax({
                type: 'POST',
                url: 'http://localhost:8080/UpdateBanner',
                contentType: 'application/json; charset=utf-8',
                data: JSON.stringify(banner),
                dataType: 'json',
                async: false,
                cache: false,
                success: function (data) {
                    console.log("update banner query is success" + data);
                    this.setState({
                        isLoaded: true,
                    });
                    if (data != null) {
                        this.state.responseFromServer = data;
                    }
                    this.componentDidMount();
                }.bind(this),
                error: function (xhr, status, err) {
                    this.componentDidMount();
                    console.error(this.props.url, status, err.toString());
                }.bind(this)
            });
        }

        this.forceUpdate();
    }

    OnClickCreateNewBanner = () => {
        this.state.isCreatingNewBanner = true;

        this.state.bannerName="";
        this.state.bannerText="";
        this.state.price="";

        this.forceUpdate();
    }

    componentDidMount(){
        this.GetAllBanners();
    }

    GetAllBanners = () => {
        $.ajax({
            type: 'POST',
            url: 'http://localhost:8080//GetBanners',
            contentType: 'application/json; charset=utf-8',
            dataType: 'json',
            async: false,
            cache: false,
            success: function(data) {
                this.setState({
                    data: data,
                    isLoaded: true
                });

                this.state.isCreatingNewBanner = true;
                data.map(item => (
                    (!item.deleted) ?
                        this.state.isCreatingNewBanner=false
                        : null
                ));

            }.bind(this),
            error: function(xhr, status, err) {
                console.error(this.props.url, status, err.toString());
            }.bind(this)
        });

        $.ajax({
            type: 'POST',
            url: 'http://localhost:8080//GetCategoryNames',
            contentType: 'application/json; charset=utf-8',
            dataType: 'json',
            async: false,
            cache: false,
            success: function(data) {
                this.setState({
                    allCategoryNames: data,
                    isLoaded: true
                });
            }.bind(this),
            error: function(xhr, status, err) {
                console.error(this.props.url, status, err.toString());
            }.bind(this)
        });

    }

    saveToStateCurrentData = (id) => {
        this.state.data.map(item => {
            if (item.id === id) {
                this.state.currentBannerId = item.id;
                this.state.bannerName = item.name;
                this.state.price = item.price;
                this.state.bannerText = item.content;
                this.state.currentCategory = item.categoryId;
            }
        });
    }

    getClassforLink = (id) => {
        if(this.state.currentBannerId==null || id == this.state.currentBannerId)
        {
            this.state.currentBannerId = id;
            this.saveToStateCurrentData(id);
            return s.activeLink;
        }
    }

    setNewCurrentBanner = (id) => {
        this.state.isCreatingNewBanner = false;
        if(this.state.responseFromServer) {
            this.componentDidMount();
            this.state.responseFromServer = false;
        }
        this.state.currentBannerId = id;
        this.saveToStateCurrentData(id);

        this.render();
        this.forceUpdate();
    }



    render() {
        const {error, isLoaded, data, allCategoryNames} = this.state;

        if (error) {
            return <p>Error</p>
        } else if (!isLoaded) {
            return <p> Loading... </p>
        } else {
            return (
                <div className = {s.editcategories}>
                    <div className = {s.nav}>
                        <p className={s.categoryLabel}>Banners:</p>

                        <input onChange={(e) => {this.OnChangeSearch(e)}}
                               defaultValue={this.state.currentSearch}
                               key={this.state.currentSearch}
                               placeholder="Поиск"
                        autoFocus
                        className={s.search}/>

                        {data.map(item => (
                            (!item.deleted && (item.name.toLowerCase().includes(this.state.currentSearch.toLowerCase()) || this.state.currentSearch=="")) ? (
                                    <div>
                                        <p onClick={() => this.setNewCurrentBanner(item.id)}
                                           className={!this.state.isCreatingNewBanner ? this.getClassforLink(item.id) : null}>{item.name}</p>
                                    </div>)
                                : null
                        ))}




                        {this.state.isCreatingNewBanner ?
                            (
                            <div>
                                <p className={s.activeLink}>New banner</p>
                            </div>
                            )
                            : null
                        }

                        <button className={s.button}
                                onClick={this.OnClickCreateNewBanner}>
                                Create new banner
                        </button>

                    </div>
                        <form className={s.formclass}>
                            {this.state.isCreatingNewBanner ?(
                                    <div className={s.inputHeading}>
                                        <p>Creating new banner</p>
                                    </div>
                                )
                                :
                                <div className={s.inputHeading}>
                                    <p>{this.state.bannerName} ID: {this.state.currentBannerId}</p>
                                </div>
                            }

                            <div className={s.inputBlock}>

                                <div>
                                <span>name</span> <input onChange={(e)=>{this.state.bannerName =e.target.value}}
                                            defaultValue={this.state.bannerName}
                                            key={this.state.bannerName}
                                            type="text"/>
                                </div>
                                <div>
                                Price <input onChange={(e)=>{this.state.price = e.target.value}}
                                                  defaultValue={this.state.price}
                                                  key={this.state.price}
                                                  type="text"/>
                                </div>
                                <div>
                                   Category <select onChange={(e)=>{this.state.currentCategory = e.target.value}}
                                                    defaultValue={this.state.currentCategory}
                                                    key={this.state.currentCategory} >
                                        {allCategoryNames.map(item => (
                                            <option value={item} >{item}</option>
                                        ))}
                                    </select>
                                </div>
                                <div>
                                    Text <textarea onChange={(e)=>{this.state.bannerText = e.target.value}}
                                                    defaultValue={this.state.bannerText}
                                                    key={this.state.bannerText}
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
                                <p onClick={(e) => {this.OnClickSaveBanner(e)}}
                                        className={s.savebutton} ><p>save</p></p>
                                <p onClick={(e)=>{this.onClickDeleteBanner(e)}}
                                   className={s.deletebutton} ><p>delete</p></p>
                            </div>
                        </form>
                    </div>


            );
        }
    }


}
