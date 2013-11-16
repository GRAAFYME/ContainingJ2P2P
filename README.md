ContainingJ2P2P
===============

Eclipse
---------------

Get the "Maven Integration for Eclipse (Juno and newer)"(570k installs) plugin from help->eclipse marketplace. 
Go to File->import: Existing Maven Projects
Select p2p as Root Directory & hit Finish

4 \*) Right-click Client->build path->configure build path: Add jars-> sellect all jars in P2P/lib 

IntelliJ
---------------

 Import module
 Select Maven
 set P2P as root dir

4)File->project structure: Modules->dependencies-> "+": Jars or Directories. Apply.
