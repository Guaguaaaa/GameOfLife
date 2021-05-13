from pynput.mouse import Controller as mouse_controller
from pynput.mouse import Button, Listener
from pynput.keyboard import Controller as keyboard_controller
from pynput.keyboard import Key
from PIL import ImageGrab
import numpy as np
import win32gui
import time
import random
import pygame

'''
This testing program will test the program by:
1. click every cells(64 x 36 = 2304) to make it alive
2. click "clear" button to clear the board
3. click "start", "stop", "speed-", "speed+" button to test if it works
4. randomly get 500 cells(it may be repeated) position and click it to make it alive.
5. click "start" button to test if it works, after 10 seconds click "stop" button to stop the generation
6. click four "type" buttons while paused game, that click "start" button and click four "type" buttons again to test if it works when paused/running
7. click "random" button and input "500" to check if it generates 500 random alive cells
'''

pygame.init()

random_cell_test = False
random_button_test = False
cells_click = False
buttons_test = False

class_name = "SunAwtFrame"
title_name = "Game of Life"

# get mouse control
mouse = mouse_controller()
keyboard = keyboard_controller()

test_hwnd = win32gui.FindWindow("pygame", "Testing Program")

# get gui window position
hwnd = win32gui.FindWindow(class_name, title_name)
if hwnd:
    left, top, right, bottom = win32gui.GetWindowRect(hwnd)

# get grid position
grid_top = top + 117
grid_left = left + 1

def check_cells():
    # press every "cells" in the grid to check if it works
    for i in range(64):
        for j in range(36):
            mouse_position_x = grid_left+20*i+15
            mouse_position_y = grid_top+20*j+5
            mouse.position = (mouse_position_x, mouse_position_y)
            time.sleep(0.05)
            mouse.click(Button.left, 1)

def clear_button():
    # press the "clear" button to clear the grid
    mouse.position = (left+117, top+70)
    time.sleep(0.1)

def start_stop_button():
    # press start button
    mouse.position = (left+900, top+80)
    time.sleep(0.1)

# speed
def speed_decrease():
    mouse.position = (left+267, top+70)
    time.sleep(0.1)
def speed_increase():
    mouse.position = (left+667, top+70)
    time.sleep(0.1)

def random_click_cells():
    # get 500 random position and press it
    for i in range(500):
        rand_x = random.randint(0, 63)
        rand_y = random.randint(0, 35)
        mouse_x = grid_left+20*rand_x+15
        mouse_y = grid_top+20*rand_y+5
        mouse.position = (mouse_x, mouse_y)
        time.sleep(0.1)
        mouse.click(Button.left, 1)

def still_alive_type():
    mouse.position = (left+117, top+870)
    time.sleep(0.1)

def oscillators_type():
    mouse.position = (left+337, top+870)
    time.sleep(0.1)

def spaceship_type():
    mouse.position = (left+337, top+870)
    time.sleep(0.1)

def gospers_glider_gun():
    mouse.position = (left+777, top+870)
    time.sleep(0.1)

def random_button(user_input):
    mouse.position = (left+1087, top+870)
    time.sleep(0.1)
    mouse.click(Button.left, 1)
    mouse.position = (left+997, top+890)
    time.sleep(0.1)
    mouse.click(Button.left, 1)
    keyboard.type(user_input)
    time.sleep(0.1)
    mouse.position = (left+1157, top+890)
    time.sleep(0.1)
    mouse.click(Button.left, 1)

def click_space():
    mouse.position = (left + 1200, top+890)
    time.sleep(0.1)
    mouse.click(Button.left, 1)  

running_p = True
while running_p:
    running = True
    while running:
        # GUI
        screen = pygame.display.set_mode((800, 500))
        pygame.display.set_caption("Testing Program")

        for event in pygame.event.get():
            if event.type == pygame.QUIT:
                running = False

        mouseX = pygame.mouse.get_pos()[0]
        mouseY = pygame.mouse.get_pos()[1]

        #initialise windows
        screen.fill((255, 255, 255))
        # [x, y, xw, yw]
        if mouseX > 200 and mouseX < 600 and mouseY > 75 and mouseY < 125:
            pygame.draw.rect(screen, (85, 85, 85), [200, 75, 400, 50])
        else:
            pygame.draw.rect(screen, (153, 153, 153), [200, 75, 400, 50])
        string_font = pygame.font.SysFont(None, 60)
        cell_check_img = string_font.render("Check Buttons", True, (0, 0, 0))
        screen.blit(cell_check_img, (250, 83))

        if mouseX > 200 and mouseX < 600 and mouseY > 200 and mouseY < 250:
            pygame.draw.rect(screen, (85, 85, 85), [200, 200, 400, 50])
        else:
            pygame.draw.rect(screen, (153, 153, 153), [200, 200, 400, 50])
        random_cell_click_img = string_font.render("User Input Simulate", True, (0, 0, 0))
        screen.blit(random_cell_click_img, (200, 208))

        if mouseX > 200 and mouseX < 600 and mouseY > 325 and mouseY < 375:
            pygame.draw.rect(screen, (85, 85, 85), [200, 325, 400, 50])
        else:
            pygame.draw.rect(screen, (153, 153, 153), [200, 325, 400, 50])
        random_button_img = string_font.render("Input Validation", True, (0, 0, 0))
        screen.blit(random_button_img, (240, 333))

        if mouseX > 0 and mouseX < 110 and mouseY > 0 and mouseY < 40:
            pygame.draw.rect(screen, (85, 85, 85), [0, 0, 110, 40])
        else:
            pygame.draw.rect(screen, (153, 153, 153), [0, 0, 110, 40])
        exit_font = pygame.font.SysFont(None, 60)
        exit_img = exit_font.render("EXIT", True, (0, 0, 0))
        screen.blit(exit_img, (0, 0))

        # mouse listenser
        event = pygame.event.poll()
        if event.type == pygame.QUIT:
            running = False
        elif event.type == pygame.MOUSEBUTTONDOWN and event.button == 1:
            if mouseX > 200 and mouseX < 600 and mouseY > 75 and mouseY < 125:
                buttons_test = True
                running = False
            elif mouseX > 200 and mouseX < 600 and mouseY > 200 and mouseY < 250:
                random_cell_test = True
                running = False
            elif mouseX > 200 and mouseX < 600 and mouseY > 325 and mouseY < 375:
                random_button_test = True
                running = False
            elif mouseX > 0 and mouseX < 110 and mouseY > 0 and mouseY < 40:
                running_p = False
                running = False

        pygame.display.update()

    win32gui.SetForegroundWindow(hwnd)
    if random_cell_test == True:
        time.sleep(3)
        clear_button()
        mouse.click(Button.left, 1)
        random_click_cells()
        # press "start" button to start the game and test if anything went wrong
        start_stop_button()
        mouse.click(Button.left, 1)
        time.sleep(10)
        mouse.click(Button.left, 1)
        #win32gui.SetForegroundWindow(test_hwnd)
        random_cell_test = False
        running = True

    if random_button_test == True:
        time.sleep(3)
        win32gui.SetForegroundWindow(hwnd)
        clear_button()
        # typical
        random_button("2306")
        time.sleep(2)
        click_space()
        random_button("-1")
        time.sleep(2)
        click_space()
        random_button("abcde")
        time.sleep(2)
        click_space()
        random_button("1.5")
        time.sleep(2)
        click_space()
        random_button("")
        time.sleep(2)
        click_space()
        # symbols
        mouse.position = (left+1087, top+870)
        time.sleep(0.1)
        mouse.click(Button.left, 1)
        mouse.position = (left+997, top+890)
        time.sleep(0.1)
        mouse.click(Button.left, 1)
        with keyboard.pressed(Key.shift):
            keyboard.press('1')
            keyboard.press('2')
            keyboard.press('3')
        time.sleep(0.1)
        mouse.position = (left+1157, top+890)
        time.sleep(0.1)
        mouse.click(Button.left, 1)

        time.sleep(2)
        click_space()

        # input valid value and run
        random_button("500")
        start_stop_button()
        mouse.click(Button.left, 1)
        time.sleep(5)
        mouse.click(Button.left, 1)
        #win32gui.SetForegroundWindow(test_hwnd)
        random_button_test = False
        running = True

    if buttons_test == True:
        time.sleep(3)
        clear_button()
        mouse.click(Button.left, 1)
        check_cells()
        clear_button()
        mouse.click(Button.left, 1)
        start_stop_button()
        mouse.click(Button.left, 1)
        time.sleep(0.5)
        start_stop_button()
        mouse.click(Button.left, 1)
        speed_decrease()
        mouse.click(Button.left, 1)
        mouse.click(Button.right, 1)
        mouse.click(Button.left, 2)
        speed_increase()
        mouse.click(Button.left, 1)
        mouse.click(Button.right, 1)
        mouse.click(Button.left, 2)
        clear_button()
        mouse.click(Button.left, 1)


        # click different "type" buttons
        clear_button()
        mouse.click(Button.left, 1)
        still_alive_type()
        mouse.click(Button.left, 1)
        oscillators_type()
        mouse.click(Button.left, 1)
        spaceship_type()
        mouse.click(Button.left, 1)
        gospers_glider_gun()
        mouse.click(Button.left, 1)
        start_stop_button()
        mouse.click(Button.left, 1)
        still_alive_type()
        mouse.click(Button.left, 1)
        oscillators_type()
        mouse.click(Button.left, 1)
        spaceship_type()
        mouse.click(Button.left, 1)
        gospers_glider_gun()
        mouse.click(Button.left, 1)
        start_stop_button()
        mouse.click(Button.left, 1)
        clear_button()
        mouse.click(Button.left, 2)
        #win32gui.SetForegroundWindow(test_hwnd)
        buttons_test = False
        running = True
