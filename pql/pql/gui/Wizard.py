# coding=utf-8
from PyQt5.QtWidgets import QApplication
from PyQt5.QtWidgets import QMainWindow
from PyQt5.QtWidgets import QScrollArea
from PyQt5.QtWidgets import QVBoxLayout
from PyQt5.QtWidgets import QWidget
from PyQt5.QtWidgets import QWizard
from PyQt5.QtWidgets import QWizardPage


class Wizard(QWizard):
    def __init__(self, parent=None):
        super(Wizard, self).__init__(parent)
        self.init_ui()

    def closeEvent(self, event):
        event.accept()

    def init_ui(self):
        self.resize(800, 600)
        self.setWindowTitle('Leuker kunnen we het niet maken')
        self.center()

    def center(self):
        frame_geometry = self.frameGeometry()
        desktop = QApplication.desktop()
        screen = desktop.screenNumber(desktop.cursor().pos())
        center_point = desktop.screenGeometry(screen).center()
        frame_geometry.moveCenter(center_point)
        self.move(frame_geometry.topLeft())

    def add_page(self, page):
        self.addPage(page)


class Page(QWizardPage, QMainWindow):
    def __init__(self, title, parent=None):
        super(Page, self).__init__(parent)
        self.setTitle(title.name)

    def set_layout(self, layout):
        widget = QWidget()
        scroll = QScrollArea()
        widget.setLayout(layout)
        scroll.setWidget(widget)
        scroll.setWidgetResizable(True)
        scroll_layout = QVBoxLayout()
        scroll_layout.addWidget(scroll)
        self.setLayout(scroll_layout)
