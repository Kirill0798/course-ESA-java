import React, { Component } from 'react';
import { Button, ButtonGroup, Container, Table } from 'reactstrap';
import AppNavbar from './AppNavbar';
import { Link } from 'react-router-dom';

class GroupList extends Component {

    constructor(props) {
        super(props);
        this.state = {groups: [], isLoading: true};
        this.remove = this.remove.bind(this);
    }

    componentDidMount() {
        this.setState({isLoading: true});

        fetch('boxes')
            .then(response => response.json())
            .then(data => this.setState({groups: data, isLoading: false}));
    }

    async remove(id) {
        await fetch(`/boxes/delete/${id}`, {
            method: 'DELETE',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            }
        }).then(() => {
            let updatedGroups = [...this.state.groups].filter(i => i.id !== id);
            this.setState({groups: updatedGroups});
        });
    }

    render() {
        const {groups, isLoading} = this.state;

        if (isLoading) {
            return <p>Loading...</p>;
        }

        const groupList = groups.map(group => {
            console.log(group);
            const {name, width, height, weight, price} = group.gift;

            return <tr key={group.id}>
                <td style={{whiteSpace: 'nowrap'}}>{group.address}</td>
                <td>{group.email}</td>
                <td>{name}</td>
                <td>{price}</td>
                <td>{`${width.toString().substring(0, 3)} x ${height.toString().substring(0,3)}`}</td>
                <td>{weight.toString().substring(0,3)}</td>
                <td>
                    <ButtonGroup>
                        <Button size="sm" color="danger" onClick={() => this.remove(group.id)}>Delete</Button>
                    </ButtonGroup>
                </td>
            </tr>
        });

        return (
            <div>
                <AppNavbar/>
                <Container fluid>
                    <div className="float-right">
                        <Button color="success" tag={Link} to="/groups/new">Add Item</Button>
                    </div>
                    <h3>Box List</h3>
                    <Table className="mt-4">
                        <thead>
                        <tr>
                            <th width="20%">City</th>
                            <th width="20%">Email</th>
                            <th width="20%">Gift</th>
                            <th>Price</th>
                            <th width="10%">Sizes</th>
                            <th>Weight</th>
                        </tr>
                        </thead>
                        <tbody>
                        {groupList}
                        </tbody>
                    </Table>
                </Container>
            </div>
        );
    }
}

export default GroupList;