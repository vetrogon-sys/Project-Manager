import React, { useState, useEffect } from 'react';
import { useSearchParams } from 'react-router-dom'
import { Box, Typography, Card, CardContent, CardActions, Button } from '@mui/material';
import PlusIcon from '@mui/icons-material/Add';
import deskController from '../../services/DeskController';
import taskController from '../../services/TaskController';

async function getDesks(projectId, setIsLoading) {
    setIsLoading(true);

    const data = await deskController(projectId).findAll()
        .then((response) => {
            return response.data;
        })
        .catch((err) => {
            return err.response;
        });

    setIsLoading(false);

    return {
        desks: data,
    };

}

async function getTasksForDeskWithId(deskId, setIsLoading) {
    setIsLoading(true);

    const data = await taskController(deskId).findAllInDesk()
        .then((response) => {
            return response.data;
        })
        .catch((err) => {
            return err.response;
        });

    setIsLoading(false);

    return {
        tasks: data,
    };

}

async function moveTaskToAnotherDesk(taskId, currentDeskId, updatedDeskId) {
    await taskController(currentDeskId).moveTaskToAnotherDesk(taskId, updatedDeskId);
}

export default function DesksList(projectId, _setLoading) {
    const [desks, setDesks] = useState();
    const [tasks, setTasks] = useState(new Map());

    useEffect(() => {
        const fetchData = async () => {
            let response = await getDesks(projectId, _setLoading);

            if (response.desks) {
                response.desks.forEach(async (desk) => {
                    let responseTasks = (await getTasksForDeskWithId(desk.id, _setLoading)).tasks;

                    setTasks(new Map(tasks.set(desk.name, responseTasks)));
                })

                setDesks(response.desks);
            }
        };

        if (!desks) {
            fetchData();
        }

    }, [])

    const onDragStart = (evt) => {
        let element = evt.currentTarget;
        element.classList.add("dragged");
        evt.dataTransfer.setData("taskId", element.id);
        evt.dataTransfer.setData("currentDeskName", element.parentElement.parentElement.id);
        evt.dataTransfer.effectAllowed = "move";
    };

    const onDragEnd = (evt) => {
        evt.currentTarget.classList.remove("dragged");
    };

    const onDragEnter = (evt) => {
        evt.preventDefault();
        let element = evt.currentTarget;
        element.classList.add("dragged-over");
        evt.dataTransfer.dropEffect = "move";
    };

    const onDragLeave = (evt) => {
        let currentTarget = evt.currentTarget;
        let newTarget = evt.relatedTarget;
        if (newTarget.parentNode === currentTarget || newTarget === currentTarget)
            return;
        evt.preventDefault();
        let element = evt.currentTarget;
        element.classList.remove("dragged-over");
    };

    const onDragOver = (evt) => {
        evt.preventDefault();
        evt.dataTransfer.dropEffect = "move";
    };

    const onDrop = (evt, deskName) => {
        evt.preventDefault();
        evt.currentTarget.classList.remove("dragged-over");
        let taskId = evt.dataTransfer.getData("taskId");
        let currentDeskName = evt.dataTransfer.getData("currentDeskName");

        let currentDeskTasks = tasks.get(currentDeskName);
        const taskToMove = currentDeskTasks.find(task => taskId === task.id.toString());
        const currentDeskUpdatedTasks = currentDeskTasks.filter(task => taskToMove !== task);
        let updatedTasks = new Map(tasks.set(currentDeskName, currentDeskUpdatedTasks));

        let updatedDeskTasks = tasks.get(deskName);
        updatedDeskTasks.push(taskToMove);

        const currentDeskId = desks.find(desk => desk.name === currentDeskName).id;
        const updatedDeskId = desks.find(desk => desk.name === deskName).id;
        moveTaskToAnotherDesk(taskId, currentDeskId, updatedDeskId);
        setTasks(new Map(updatedTasks.set(deskName, updatedDeskTasks)));
    };

    const getTaskElement = (task) => {
        return (
            <Card
                key={task.id}
                id={task.id}
                sx={{
                    textAlign: 'left',
                    margin: '1rem 0',
                }}
                draggable
                onDragStart={(e) => onDragStart(e)}
                onDragEnd={(e) => onDragEnd(e)}>
                <CardContent sx={{
                    padding: '.5rem .5rem 0 .5rem'
                }}>
                    <Typography sx={{ fontSize: 12 }} color="text.secondary" gutterBottom>
                        Task
                    </Typography>
                    <Typography variant="h6" component="div" >
                        {task.title}
                    </Typography>
                    <Typography sx={{ fontSize: 12 }} color="text.secondary">
                        adjective
                    </Typography>
                    <Typography variant="body3">
                        {task.description ? task.description : 'There isn\'t descritpion here...'}
                    </Typography>
                </CardContent>
                <CardActions sx={{
                    padding: '.1rem .5rem'
                }}>
                    <Button size="small">Learn More</Button>
                </CardActions>
            </Card>
        )
    }

    const getDeskTasks = (desk) => {
        if (tasks.size === 0) {
            return <div></div>
        }

        const tasksList = tasks.get(desk.name);
        if (!tasksList) {
            return <div></div>
        }

        return tasksList
            .map(task => getTaskElement(task));
    }

    const getDeskContainer = (desk) => {

        return (
            <Box
                key={desk.id}
                id={desk.name}
                sx={{
                    minWidth: '250',
                    maxWidth: '370',
                    width: '100%',
                    margin: '.7rem',
                    backgroundColor: 'rgb(245, 247, 252)',
                    height: 'fit-content',
                    maxHeight: '100%',
                    borderRadius: '.3rem',
                    display: 'flex',
                    flexDirection: 'column'
                }
                }
                onDragLeave={(e) => onDragLeave(e)}
                onDragEnter={(e) => onDragEnter(e)}
                onDragEnd={(e) => onDragEnd(e)}
                onDragOver={(e) => onDragOver(e)}
                onDrop={(e) => onDrop(e, desk.name)}
            >
                <Box
                    sx={{
                        display: 'flex',
                        justifyContent: 'center',
                        alignItems: 'center',
                        width: '100%',
                        backgroundColor: 'rgb(199, 208, 235)',
                        minHeight: '3rem',
                        fontWeight: 400
                    }}
                >
                    {desk.name}
                </Box>
                <Box sx={{
                    margin: '.5rem',
                    overflow: "auto",
                    height: '100%'
                }}>
                    {getDeskTasks(desk)}
                </Box>
                <Box sx={{
                    alignSelf: 'flex-end',
                }}>
                    <Button sx={{
                        margin: '.5rem',
                    }}>
                        <PlusIcon />
                        Create task
                    </Button>
                </Box>
            </Box >
        )
    }

    const getDesksContainers = () => {
        if (!desks) {
            return <div></div>;
        }

        return desks.map(
            (desk) => getDeskContainer(desk)
        );
    }

    return (
        <div style={{
            marginTop: '1rem',
            display: 'flex',
            flexDirection: 'row',
            justifyContent: 'space-between',
            width: '95%',
            height: '90%'
        }}>
            {getDesksContainers()}
        </div>
    );

}