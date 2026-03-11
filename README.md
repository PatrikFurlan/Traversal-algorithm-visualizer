# Traversal algorithm visualizer
>[!NOTE]
>**The current version is available on the *development* branch**

TAV is a program to help step-by-step visualization of traversal algorithms such as DFS (others have yet to be implemented), including backtracking. The program allows for custom graph drawing by placing nodes and drawing edges between them. After inputting the starting and ending node on the left panel, the simulation can be started. When stepping through nodes, the edges connecting them will be highlighted with either **RED** if the edge is part of the final path or **BLUE** if the current edge isn't part of the final path, in which case further steps will show backtracking by resetting the edges back to their initial state.

### Placing graph nodes

To place a node, left-click anywhere on the canvas. You can also move nodes around by dragging them with your mouse.

Drawing edges between nodes

To begin drawing an edge between nodes, right-click a node. The node will change color, which signals the node being selected and waiting for another node to be selected. Right-click on another available node to connect them.

### Starting the simulation

To start the simulation, enter the starting and ending node letter values in the from and to text fields. After doing so, you can start the simulation by clicking on the _START_ button below next to the _RESET_ button (the _RESET_ button will delete your graph). To keep moving through the steps, keep pressing the same button. While the simulation is running, you are unable to add nodes or edges, but you can still move nodes around.
