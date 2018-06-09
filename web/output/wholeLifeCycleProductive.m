clear;
close all

costCol = 1:11;
effortCol = 12:17;
pureNumWholeWeek0 = xlsread('analyze.xls');
normalizedPureNumberWholeWeek0 = mapminmax(pureNumWholeWeek0',0,1)';
sumWeeklyEffort0 = sum(normalizedPureNumberWholeWeek0(:,effortCol),2);
sumWeeklyCost0 = sum(normalizedPureNumberWholeWeek0(:,costCol),2);
productivity0 = sumWeeklyEffort0./sumWeeklyCost0;
figure('name','whole lifecycle')
hold on;
plot(productivity0,'-x')
%plot(normalizedPureNumberWholeWeek0(:,13))

pureNumWholeWeek4 = xlsread('analyzeV4.xls');
normalizedPureNumberWholeWeek4 = mapminmax(pureNumWholeWeek4',0,1)';
sumWeeklyEffort4 = sum(normalizedPureNumberWholeWeek4(:,effortCol),2);
sumWeeklyCost4 = sum(normalizedPureNumberWholeWeek4(:,costCol),2);
productivity4 = sumWeeklyEffort4./sumWeeklyCost4;
traditionalProductivity4 = pureNumWholeWeek4(:,2)./pureNumWholeWeek4(:,13);

pureNumWholeWeek5 = xlsread('analyzeV5.xls');
normalizedPureNumberWholeWeek5 = mapminmax(pureNumWholeWeek5',0,1)';
sumWeeklyEffort5 = sum(normalizedPureNumberWholeWeek5(:,effortCol),2);
sumWeeklyCost5 = sum(normalizedPureNumberWholeWeek5(:,costCol),2);
productivity5 = sumWeeklyEffort5./sumWeeklyCost5;
traditionalProductivity5 = pureNumWholeWeek5(:,2)./pureNumWholeWeek5(:,13);

pureNumWholeWeek6 = xlsread('analyzeV6.xls');
normalizedPureNumberWholeWeek6 = mapminmax(pureNumWholeWeek6',0,1)';
sumWeeklyEffort6 = sum(normalizedPureNumberWholeWeek6(:,effortCol),2);
sumWeeklyCost6 = sum(normalizedPureNumberWholeWeek6(:,costCol),2);
productivity6 = sumWeeklyEffort6./sumWeeklyCost6;
traditionalProductivity6 = pureNumWholeWeek6(:,2)./pureNumWholeWeek6(:,13); 

figure('name','three versions by week')
clf;
hold on;
plot(productivity4,'-o')
plot(productivity5,'-s')
plot(productivity6,'-x')
hold off;

figure('name','v4 normal and traditional')
clf;
hold on;
plot(productivity4,'-o')
plot(normalizedPureNumberWholeWeek4(:,13),'-x')
hold off;

figure('name','v5 normal and traditional')
clf;
hold on;
plot(productivity5,'-o')
plot(normalizedPureNumberWholeWeek5(:,13),'-x')
hold off;

figure('name','v6 normal and traditional')
clf;
hold on;
plot(productivity6,'-o')
plot(normalizedPureNumberWholeWeek6(:,13),'-x')
hold off;