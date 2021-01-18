import React, { Component } from 'react';
import { Link, withRouter } from 'react-router-dom';
import { Button, Container, Form, FormGroup, Input, Label } from 'reactstrap';
import AppNavbar from './AppNavbar';

class GroupEdit extends Component {

    emptyBox = {
        address: '',
        email: '',
        gift: this.emptyGift
    };

    emptyGift = {
        name: '',
        width: '',
        height: '',
        weight: '',
        price: ''
    };

    constructor(props) {
        super(props);
        this.state = {
            item: this.emptyBox,
            itemGift: this.emptyGift
        };
        this.handleChangeBox = this.handleChangeBox.bind(this);
        this.handleChangeGift = this.handleChangeGift.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
    }

    async componentDidMount() {
        if (this.props.match.params.id !== 'new') {
            const group = await (await fetch(`/boxes/${this.props.match.params.id}`)).json();
            this.setState({item: group});
        }
    }

    handleChangeBox(event) {
        const target = event.target;
        const value = target.value;
        const name = target.name;
        let item = {...this.state.item};
        item[name] = value;
        this.setState({item});
    }

    handleChangeGift(event){
        const target = event.target;
        const value = target.value;
        const name = target.name;
        let itemGift = {...this.state.itemGift};
        let item = {...this.state.item};
        itemGift[name] = value;
        item["gift"] = itemGift
        this.setState({itemGift});
        this.setState({item});
    }

    async handleSubmit(event) {
        event.preventDefault();
        const {item} = this.state;

        await fetch('/boxes/add' + (item.id ? '/' + item.id : ''), {
            method: (item.id) ? 'PUT' : 'POST',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(item),
        });
        this.props.history.push('/groups');
    }

    render() {
        const {item, itemGift} = this.state;
        console.log(item);
        console.log(itemGift);
        const title = <h2>{item.id ? 'Edit Item' : 'Add Item'}</h2>;

        return <div>
            <AppNavbar/>
            <Container>
                {title}
                <Form onSubmit={this.handleSubmit}>
                    <FormGroup>
                        <Label for="address">Address</Label>
                        <Input type="text" name="address" id="address" value={item.address || ''}
                               onChange={this.handleChangeBox} autoComplete="name"/>
                    </FormGroup>
                    <FormGroup>
                        <Label for="email">Email</Label>
                        <Input type="text" name="email" id="email" value={item.email || ''}
                               onChange={this.handleChangeBox} autoComplete="name"/>
                    </FormGroup>
                    <FormGroup>
                        <Label for="name">Name</Label>
                        <Input type="text" name="name" id="name" value={itemGift.name || ''}
                               onChange={this.handleChangeGift} autoComplete="address-level1"/>
                    </FormGroup>
                    <FormGroup>
                        <Label for="price">Price</Label>
                        <Input type="text" name="price" id="price" value={itemGift.price || ''}
                               onChange={this.handleChangeGift} autoComplete="address-level1"/>
                    </FormGroup>
                    <div className="row">
                        <FormGroup className="col-md-4 mb-3">
                            <Label for="width">Width</Label>
                            <Input type="text" name="width" id="width" value={itemGift.width || ''}
                                   onChange={this.handleChangeGift} autoComplete="address-level1"/>
                        </FormGroup>
                        <FormGroup className="col-md-5 mb-3">
                            <Label for="height">Height</Label>
                            <Input type="text" name="height" id="height" value={itemGift.height || ''}
                                   onChange={this.handleChangeGift} autoComplete="address-level1"/>
                        </FormGroup>
                        <FormGroup className="col-md-3 mb-3">
                            <Label for="weight">Weight</Label>
                            <Input type="text" name="weight" id="weight" value={itemGift.weight || ''}
                                   onChange={this.handleChangeGift} autoComplete="address-level1"/>
                        </FormGroup>
                    </div>
                    <FormGroup>
                        <Button color="primary" type="submit">Save</Button>{' '}
                        <Button color="secondary" tag={Link} to="/groups">Cancel</Button>
                    </FormGroup>
                </Form>
            </Container>
        </div>
    }
}

export default withRouter(GroupEdit);