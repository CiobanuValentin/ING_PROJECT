package com.api.service.util;

import com.internationalization.Messages;
import com.spring.ApplicationContextFactory;
import com.util.enums.Language;
import org.mockito.MockedStatic;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;

public class MockUtil {

    public static void mockMessagesThenCallMethod(VoidFunction function) {
        try (MockedStatic<ApplicationContextFactory> utilities = mockStatic(ApplicationContextFactory.class)) {
            ReloadableResourceBundleMessageSource mockedResourceBundleMessageSource = mock(ReloadableResourceBundleMessageSource.class);
            utilities.when(() -> ApplicationContextFactory.getBean("messageSource", ReloadableResourceBundleMessageSource.class))
                    .thenReturn(mockedResourceBundleMessageSource);

            try (MockedStatic<Messages> messagesMockedStatic = mockStatic(Messages.class)) {
                messagesMockedStatic.when(() -> Messages.get(any(), (Language) any())).thenReturn(null);
                function.call();
            }

        }
    }

}
