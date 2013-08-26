Software Requirement Specification
==================================

Overall Description
-------------------

Product Functionality

• User can easily view the video lecture present in the SD-Card or on a server.

• The user can easily navigate through the video lecture using the tree functionality of this product.

• The user can store the videos by adding and updating the bookmark feature.

• The user can easily watch the videos along with transcripts.

• The user can take the quiz for self evaluation.

Operating Environment

• Any android operating system supported device.

• Android 2.2 or greater version of android operating system.

Design and Implementation Constraints

• Video format MP4(H.264) is not compatible with this software running Android 2.2 OS or above.

User Documentation

• User will be provided with the user manual along with the software.

Assumptions and Dependencies

• The deadline must be met.

• The product must be reliable.

• The product should provide best performance.

• It should provide user-friendly environment.

• The architecture must be open so that additional functionality may be added later.

• Tools we are going to use
  JDK 1.6 or later
JRE 1.6 or later
Eclipse
Android SDK
ADT Plugins
Aakash Tablet
• From the very start of this project we are aware of time
constraints so the main emphasis is on extensibility and
parallel development. We shall try our best to ensure that
project deadlines are met.
2.3 Specific Requirements
2.3.1 External Interface requirements
User Interfaces
• User interface must be user-friendly. The user interface
shall be designed using various components available in
ADT plugins such as video view for playing video lecture,
expandable list view to display the tree structure to
navigate through the video lecture, sliding drawer for
hiding and showing the content and drag and drop the
videos for viewing purpose.
Hardware Interfaces
• Any android operating system supported device.
• SD-Card to view the video lecture.
Software Interfaces
• The Eclipse Indigo 3.7 shall be used as development
environment implementing the modules.
• Designing of modules and diagrams is done using
YUML.
Communications Interfaces
• WI-FI connectivity will be required to view video
lecture present on the server.
2.3.2 Functional Requirements
Navigate through the lecture: This software shall help the user to
navigate through the video lecture so that user can jump to any desired
position in the video lecture.
BookMark: This software shall help the user to bookmark the
particular part of the video lecture which is then stored in database so
that the user can watch that video lecture again from that particular
time.
Transcripts: By using this user can easily get lecture videos
Quiz: This section helps the user to attend quiz in order to check their
performance.
2.3.3 Behavioral Requirements
Use case Diagram for Application
Fig(2.3.3)
Use case
Lecture
Fig(2.3.4)
Diagram
for
Video
Use case
Marking
Diagram
for
Book
Fig(2.3.5)
Use case Diagram for
Videos with Subtitles
Viewing
Fig(2.3.6)
Use case Diagram for Quiz Session
Fig(2.3.7)
2.4 Other Non-Functional Requirements
Maintainability: Software needs to be upgraded if required in future.
Reliability: System must be reliable and data should persist even after
suffering some system crashes or booting of android supported devices.
Portability: We are using Java to make the software more portable so
that it can run on any java enabled mobile phone.
Performance: We are using JSON so we can make the search easier
and faster so it provides better efficiency when compared to others.
Flexibility: It is flexible according to the user and it provides friendly
environment to the user.
