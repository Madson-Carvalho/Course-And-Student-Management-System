package com.courseAndStudentManagementSystem.integration;

import org.junit.Test;
import org.openqa.selenium.By;

public class SeleniumTeacherTests extends BaseSeleniumTest {

  @Test
  public void createTeacher() {
    navigateTo();

    click(By.cssSelector("path"));
    click(By.cssSelector(".MuiListItem-root:nth-child(4) .MuiListItemIcon-root"));
    click(By.cssSelector(".MuiButton-root"));

    typeText(By.id(":r19:"), "João Cleber");
    typeText(By.id(":r1b:"), "joaocleber@gmail.com");

    click(By.cssSelector(".MuiButton-root"));
    waitForUrlToBe("http://localhost:3000/teachers");
  }

  @Test
  public void listTeacher() {
    navigateTo();
    click(By.cssSelector(".MuiSvgIcon-root"));
    click(By.cssSelector(".MuiListItem-root:nth-child(4) .MuiTypography-root"));
    waitForUrlToBe("http://localhost:3000/teachers");
  }
}
